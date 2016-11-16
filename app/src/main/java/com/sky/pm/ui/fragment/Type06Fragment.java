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
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

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


    private void getData() {
        HttpDataUtils.DMS_T_DATA_SOURCEGetListByPageByJson(page + "", new IDataResultImpl<List<Latest>>() {
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
