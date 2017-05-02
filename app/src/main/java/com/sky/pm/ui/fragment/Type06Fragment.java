package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.TypeRealAdapter;
import com.sky.pm.utils.DateTimePickDialogUtil;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_type06)
public class Type06Fragment extends BaseFragment {

    private TypeRealAdapter adapter;
    @ViewInject(R.id.swipe)
    protected SwipeRefreshLayout swipe;
    @ViewInject(R.id.recycle)
    protected RecyclerView recyclerView;
    protected LinearLayoutManager mLayoutManager;

    protected int firstVisibleItem;
    protected int lastVisibleItem;
    protected int page = 1;
    protected int total = 0;//总条数
    protected boolean isRefresh = false;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0://swipe刷新操作
                    isRefresh = true;
                    page = 1;
                    clearAdapter();
                    swipe.setRefreshing(false);
                    getData();
                    break;
                case 1:
                    page++;
                    getData();
                    setSnack(recyclerView, "正在加载，请稍后", "ok");
                    break;
                case 2:
                    setSnack(recyclerView, "已无更多", "ok");
                    break;
            }
            return false;
        }
    });

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRealView();
        setSeek();
        getTotal();
    }


    private void getTotal() {
        HttpDataUtils.DMS_T_DATA_SOURCEGetRecordCount(new IDataResultImpl<Integer>() {
            @Override
            public void onSuccessData(Integer data) {
                total = data;
                getData();
            }
        });
    }

    private boolean inquiry = false;

    public void setSeek() {
        activity.tvInquiry.setVisibility(View.VISIBLE);
        activity.bt02.setVisibility(View.VISIBLE);
        activity.bt01.setText("开始时间");
        activity.bt02.setText("结束时间");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        final String str = formatter.format(curDate);

        activity.bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), str);
                dateTimePicKDialog.dateTimePicKDialog(activity.bt01);
            }
        });
        activity.bt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), str);
                dateTimePicKDialog.dateTimePicKDialog(activity.bt02);
            }
        });

        activity.tvInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInquiry();
            }
        });
        activity.btSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = activity.tv01.getText().toString().trim();
                String name = activity.tv02.getText().toString().trim();
                String begin = activity.bt01.getText().toString().trim() + ":00";
                String end = activity.bt02.getText().toString().trim() + ":00";
                if (begin.equals("开始时间:00")) begin = "";
                if (end.equals("结束时间:00")) end = "";
                //yyyy-MM-dd HH:mm:ss  2016-11-22 23:11:24
                HttpDataUtils.DMS_T_DATA_SOURCEGetListByPageByJson(
                        station, name, begin, end,
//                        "2016-11-21 23:11:24", "2016-11-22 23:11:24",
                        page + "", new IDataResultImpl<List<Latest>>() {
                            @Override
                            public void onSuccessData(List<Latest> data) {
                                if (data == null) {
                                    showToast("暂无数据");
                                    return;
                                }
                                if (isRefresh) {
                                    isRefresh = false;
                                    showToast("已刷新");
                                    Toast.makeText(getActivity(), "已刷新", Toast.LENGTH_SHORT).show();
                                }
                                if (data.size() == 0) showToast("暂无数据");
                                if (page == 1) {
                                    adapter.clearDatas();
                                    adapter.setDatas(data);
                                    recyclerView.smoothScrollToPosition(0);
                                } else adapter.addDatas(data);
                            }
                        });

            }
        });
    }

    public void setInquiry() {
        if (inquiry) {
            activity.layout01.setVisibility(View.GONE);
            activity.layout02.setVisibility(View.GONE);
            activity.layout03.setVisibility(View.GONE);
            inquiry = false;
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek01));
        } else {
            activity.layout01.setVisibility(View.VISIBLE);
            activity.layout02.setVisibility(View.VISIBLE);
            activity.layout03.setVisibility(View.VISIBLE);
            inquiry = true;
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek01));
        }
    }


    private void getData() {
        String station = activity.tv01.getText().toString().trim();
        String name = activity.tv02.getText().toString().trim();
        String begin = activity.bt01.getText().toString().trim() + ":00";
        String end = activity.bt02.getText().toString().trim() + ":00";
        if (begin.equals("开始时间:00")) begin = "";
        if (end.equals("结束时间:00")) end = "";
        HttpDataUtils.DMS_T_DATA_SOURCEGetListByPageByJson(station, name, begin, end,
                page + "", new IDataResultImpl<List<Latest>>() {
                    @Override
                    public void onSuccessData(List<Latest> data) {
                        if (data == null) {
                            showToast("暂无数据");
                            return;
                        }
                        if (isRefresh) {
                            isRefresh = false;
                            showToast("已刷新");
                            Toast.makeText(getActivity(), "已刷新", Toast.LENGTH_SHORT).show();
                        }
                        if (data.size() == 0) showToast("暂无数据");
                        if (page == 1) {
                            adapter.setDatas(data);
                            recyclerView.smoothScrollToPosition(0);
                        } else adapter.addDatas(data);
                    }
                });
    }

    private void setRealView() {
        //设置swipe的开始位置与结束位置
        swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                        .getDisplayMetrics()));
        //为进度圈设置颜色
        swipe.setColorSchemeColors(R.color.red, R.color.white, R.color.black);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(0, 3000);

            }
        });//监听
        recyclerView.setHasFixedSize(true);
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new TypeRealAdapter(R.layout.adapter_real);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if (total > adapter.getItemCount()) handler.sendEmptyMessageDelayed(1, 000);
                    else handler.sendEmptyMessageDelayed(2, 000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //  dx：大于0，向右滚动    小于0，向左滚动
                //  dy：大于0，向上滚动    小于0，向下滚动
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (firstVisibleItem > 0) swipe.setEnabled(false);
                else swipe.setEnabled(true);
            }
        });
    }

    public void setSnack(final View view, String text1, String text2) {
        Snackbar.make(view, text1, Snackbar.LENGTH_SHORT).setAction(text2, null).show();
    }

    public void clearAdapter() {
        if (adapter.getDatas() != null) adapter.getDatas().clear();
    }
}
