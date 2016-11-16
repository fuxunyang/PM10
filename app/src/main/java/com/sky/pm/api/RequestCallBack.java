package com.sky.pm.api;

import com.sky.pm.ui.MyApplication;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/12/22 下午1:25
 */
public abstract class RequestCallBack<ResultType> implements Callback.CommonCallback<ResultType> {
    private final IDataResult resultCB;

    public RequestCallBack(IDataResult callback) {
        this.resultCB = callback;
    }

    @Override
    public abstract void onSuccess(ResultType result);

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if (ex instanceof HttpException) { // 网络错误
            HttpException e = (HttpException) ex;
            MyApplication.getInstance().showError(e.getCode());

            //resultCB.onFailure(e, e.getCode());
            //ErrorLogActivity.startThis(ex.getMessage() + "");
        } else if (ex instanceof NullPointerException) {

        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        resultCB.onCancel();
    }

    @Override
    public void onFinished() {
        resultCB.onFinish();
    }
}
