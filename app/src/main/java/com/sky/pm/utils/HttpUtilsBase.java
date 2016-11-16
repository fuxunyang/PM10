package com.sky.pm.utils;


import com.sky.pm.common.Constants;
import org.xutils.http.RequestParams;

public class HttpUtilsBase {
    public static RequestParams getRequestParams(String path, String action) {
//        personal/asset/scattered/list?sortx=11
        return new RequestParams(String.format("%s%s/%s", Constants.BASE_URL, path, action));
    }

    public interface RequestHandler {
        void cancel();
    }
}