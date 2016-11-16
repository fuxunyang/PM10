package com.sky.pm.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sky.pm.R;


/**
 * @author LiBin
 * @ClassName: DialogManager
 * @Description: dialog管理类
 * @date 2015年3月26日 下午5:51:37
 */
public class DialogManager {
    private static Dialog loadingDialog;

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @return
     */
    private static Dialog createDialog(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);// 得到加载view
        ImageView spaceshipImage = (ImageView) view.findViewById(R.id.img);
        spaceshipImage.setBackgroundResource(R.drawable.dialog_rotate);
        AnimationDrawable drawable = (AnimationDrawable) spaceshipImage.getBackground();
        drawable.start();
        loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

//        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loadingDialog = null;
            }
        });
        return loadingDialog;
    }


    public static void showDialog(Context context) {
        if (loadingDialog == null)
            createDialog(context);
        loadingDialog.show();
    }

    public static void disDialog() {
        if (loadingDialog == null)
            return;
        loadingDialog.dismiss();
    }


    public interface EnterOrBackDialogListener {

        void onWarningDialogOK(int id);

        void onWarningDialogCancel(int id);
    }

    /**
     * @param context      上下文参数
     * @param id           dialog的id
     * @param warning      警告的内容
     * @param title        标题
     * @param positiveText
     * @param negativeText 标题
     * @param listener     重写方法
     * @return
     */
    public static Dialog createYesNoWarningDialog(final Context context,
                                                  final int id, String warning, String title, String positiveText, String negativeText,
                                                  final EnterOrBackDialogListener listener) {

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(warning)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (context instanceof Activity) {
                            ((Activity) context).removeDialog(id);
                        }
                        if (listener != null)
                            listener.onWarningDialogOK(id);
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (context instanceof Activity) {
                            ((Activity) context).removeDialog(id);

                            if (listener != null)
                                listener.onWarningDialogCancel(id);
                        }
                    }
                }).create();
    }


}
