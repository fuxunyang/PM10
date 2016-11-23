package com.sky.pm.api;


import org.json.JSONException;
import org.xutils.ex.HttpException;

/**
 * @author sky QQ:1136096189
 * @Description: 网络请求时使用的接口
 * @date 15/11/20 下午1:59
 */
public interface IDataResult<T> {
    void onFailure(HttpException exception, int Code);

    /**
     * 可加载progress
     *
     * @param total
     * @param current
     * @param isUploading
     */
    void onLoading(long total, long current, boolean isUploading);

    /**
     * 根据请求返回data
     *
     * @param data
     */
    void onSuccessData(T data) throws JSONException;

    void onStart();

    void onCancel();

    void onFinish();
}
