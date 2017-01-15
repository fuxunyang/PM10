package com.sky.pm.utils;

import com.google.gson.Gson;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.api.RequestCallBack;
import com.sky.pm.common.Constants;
import com.sky.pm.model.ApiResponse;
import com.sky.pm.model.Latest;
import com.sky.pm.model.Level;
import com.sky.pm.model.NewsModel;
import com.sky.pm.model.User;
import com.sky.pm.model.WeatherEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * @author sky
 * @ClassName: HttpDataUtils
 * @Description: TODO 数据请求类，待修改
 * @date 2015年4月12日 下午1:33:47
 */
public class HttpDataUtils extends HttpUtilsBase {

    /**
     * 获取天气信息
     *
     * @param lnglat
     * @param callback
     */
    public static void getWeather(String lnglat, final IDataResultImpl<WeatherEntity> callback) {
        RequestParams params = new RequestParams("http://apis.baidu.com/heweather/weather/free");
        params.addQueryStringParameter("city", lnglat);
        params.addHeader("apikey", Constants.AK);
        x.http().get(params, new RequestCallBack<String>(callback) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    JSONArray array = json.getJSONArray("HeWeather data service 3.0");
                    JSONObject jsonObject = (JSONObject) array.get(0);
                    WeatherEntity weather = new Gson().fromJson(jsonObject.toString(), WeatherEntity.class);

                    if (result != null) callback.onSuccessData(weather);
                    else callback.onSuccessData(null);
                } catch (JSONException e) {
                    callback.onSuccessData(null);
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 登录接口
     *
     * @param name
     * @param pass
     * @param callback
     */
    public static void APPUsersGetRecordCount(String name, String pass, final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "APPUsersService.asmx/APPUsersGetRecordCount");
        params.addBodyParameter("json", "[{\"Compare\":\"=\",\"FieldName\":\"Name\",\"FieldValue\":\"" + name + "\",\"IsLike\":false},{\"Compare\":\"=\",\"FieldName\":\"Password\",\"FieldValue\":\"" + pass + "\",\"IsLike\":false}] ");
        params.setCharset("gbk");
        x.http().get(params, new RequestCallBack<String>(callback) {
            @Override
            public void onSuccess(String result) {
                callback.onSuccessData(result);
            }
        });
    }

    /**
     * 注册
     *
     * @param name
     * @param pass
     * @param callback
     */
    public static void APPUsersAdd(String name, String pass, String nick, final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "APPUsersService.asmx/APPUsersAdd");
        params.addBodyParameter("json", "{\"Id\":1,\"Name\":\"" + name + "\",\"NickName\":\"" + nick + "\",\"Password\":\"" + pass + "\",\"Phone\":\"" + name + "\"}");
        params.setCharset("gbk");
        x.http().get(params, new RequestCallBack<String>(callback) {
            @Override
            public void onSuccess(String result) {
                callback.onSuccessData(result.toString());
            }
        });
    }

    public static void APPUsersGetIListByJson(String name, String pass, final IDataResultImpl<List<User>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "APPUsersService.asmx/APPUsersGetIListByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"=\",\"FieldName\":\"Name\",\"FieldValue\":\"" + name + "\",\"IsLike\":false},{\"Compare\":\"=\",\"FieldName\":\"Password\",\"FieldValue\":\"" + pass + "\",\"IsLike\":false}] ");
        params.setCharset("gbk");
        x.http().get(params, new RequestCallBack<ApiResponse<List<User>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<User>> result) {
                LogUtil.i("khfkladjfa");
                if (result != null)
                    callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 厂区监测 AQI指数
     *
     * @param callback
     */
    public static void DMS_T_DATA_LATESTGetAllListInfoByJson(final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_LATESTService.asmx/DMS_T_DATA_LATESTGetAllListInfoByJson");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                LogUtil.d(result + "dfsdfdfsdfsd");
                callback.onSuccessData(result.getRows());
//                if (result.getTotal() == 0) callback.onSuccessData(false);
//                else callback.onSuccessData(true);
            }
        });
    }

    /**
     * 站点监测
     *
     * @param callback
     */
    public static void DMS_T_DATA_LATESTGetIListInfoByJson(final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_LATESTService.asmx/DMS_T_DATA_LATESTGetIListInfoByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":true}]");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 站点查询单个
     *
     * @param station
     * @param name
     * @param callback
     */
    public static void DMS_T_DATA_LATESTGetIListInfoByJson(String station, String name, final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_LATESTService.asmx/DMS_T_DATA_LATESTGetIListInfoByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"" + name + "\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"" + station + "\",\"IsLike\":true}]");
        params.setCharset("gbk");
        x.http().get(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 模范网格
     *
     * @param callback
     */
    public static void DMS_T_DATA_LATESTGetIListByJson(final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_LATESTService.asmx/DMS_T_DATA_LATESTGetIListByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\"=\",\"FieldName\":\"AQILevel\",\"FieldValue\":\"\",\"IsLike\":false}]");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * seek
     *
     * @param callback
     */
    public static void DMS_T_DATA_LATESTGetIListByJson(String station, String name, String level,
                                                       final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_LATESTService.asmx/DMS_T_DATA_LATESTGetIListByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"" + name + "\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"" + station + "\",\"IsLike\":true},{\"Compare\":\"=\",\"FieldName\":\"AQILevel\",\"FieldValue\":\"" + level + "\",\"IsLike\":false}]");
        params.setCharset("gbk");
        x.http().get(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 模范网格 查询等级
     * @param callback
     */
    public static void NMS_T_CFG_AQIGetAllListByJson(final IDataResultImpl<List<Level>> callback) {
        //http://218.57.204.52:8018/NMS_T_CFG_AQIService.asmx?op=NMS_T_CFG_AQIGetAllListByJson
        RequestParams params = new RequestParams(Constants.BASE_URL + "NMS_T_CFG_AQIService.asmx/NMS_T_CFG_AQIGetAllListByJson");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Level>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Level>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }


    /**
     * 1小时
     *
     * @param callback
     */
    public static void DMS_T_DATA_MINUTEGetListByPageByJson(String id, final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_MINUTEService.asmx/DMS_T_DATA_MINUTEGetListByPageByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"=\",\"FieldName\":\"StationId\",\"FieldValue\":\"" + id + "\",\"IsLike\":false}]");
        params.addBodyParameter("orderby", "Id desc");
        params.addBodyParameter("pageIndex", "1");
        params.addBodyParameter("pageSize", "24");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 24小时
     *
     * @param callback
     */
    public static void DMS_T_DATA_HOURGetListByPageByJson(String id, final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_HOURService.asmx/DMS_T_DATA_HOURGetListByPageByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"=\",\"FieldName\":\"StationId\",\"FieldValue\":\"" + id + "\",\"IsLike\":false}]");
        params.addBodyParameter("orderby", "Id desc");
        params.addBodyParameter("pageIndex", "1");
        params.addBodyParameter("pageSize", "24");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 设备管理
     *
     * @param callback
     */
    public static void NMS_T_CFG_SITE_INFOGetIListByJson(final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "NMS_T_CFG_SITE_INFOService.asmx/NMS_T_CFG_SITE_INFOGetIListByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":true}]\n");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    public static void NMS_T_CFG_SITE_INFOGetIListByJson(String station, String name, final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "NMS_T_CFG_SITE_INFOService.asmx/NMS_T_CFG_SITE_INFOGetIListByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"" + name + "\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"" + station + "\",\"IsLike\":true}]");
        params.setCharset("gbk");
        x.http().get(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 历史数据
     *
     * @param callback
     */
    public static void DMS_T_DATA_SOURCEGetListByPageByJson(String page, final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_SOURCEService.asmx/DMS_T_DATA_SOURCEGetListByPageByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\">=\",\"DataTime\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":false},{\"Compare\":\"<=\",\"DataTime\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":false}]\n");
        params.addBodyParameter("orderby", "Id desc");
        params.addBodyParameter("pageIndex", page);
        params.addBodyParameter("pageSize", "10");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 历史数据
     *
     * @param callback
     */
    public static void DMS_T_DATA_SOURCEGetListByPageByJson(String station, String name, String begin, String end, String page, final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_SOURCEService.asmx/DMS_T_DATA_SOURCEGetListByPageByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"" + name + "\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"" + station + "\",\"IsLike\":true},{\"Compare\":\">=\",\"FieldName\":\"DataTime\",\"FieldValue\":\"" + begin + "\",\"IsLike\":false},{\"Compare\":\"<=\",\"FieldName\":\"DataTime\",\"FieldValue\":\"" + end + "\",\"IsLike\":false}]");
        params.addBodyParameter("orderby", "Id desc");
        params.addBodyParameter("pageIndex", page);
        params.addBodyParameter("pageSize", "10");
        params.setCharset("gbk");
        x.http().get(params, new RequestCallBack<ApiResponse<List<Latest>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<Latest>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }

    /**
     * 历史数据总条数
     *
     * @param callback
     */
    public static void DMS_T_DATA_SOURCEGetRecordCount(final IDataResultImpl<Integer> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_SOURCEService.asmx/DMS_T_DATA_SOURCEGetRecordCount\n");
        params.addBodyParameter("json", "[{\"Compare\":\"like\",\"FieldName\":\"StationName\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\"like\",\"FieldName\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":true},{\"Compare\":\">=\",\"DataTime\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":false},{\"Compare\":\"<=\",\"DataTime\":\"Stationmn\",\"FieldValue\":\"\",\"IsLike\":false}]\n");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<Integer>(callback) {
            @Override
            public void onSuccess(Integer result) {
                callback.onSuccessData(result);
            }
        });
    }

    /**
     * 企业风采
     *
     * @param callback
     */
    public static void NewsGetAllListByJson(final IDataResultImpl<List<NewsModel>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "NewsService.asmx/NewsGetAllListByJson");
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<NewsModel>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<NewsModel>> result) {
                callback.onSuccessData(result.getRows());
            }
        });
    }
    /**
     * 检查更新
     *
     * @param callback
     * @return
     */
    public static RequestHandler checkUpdate(final IDataResultImpl<Integer> callback) {
        //http://192.168.1.132:8033/LiHuoService.asmx/
        RequestParams params = new RequestParams(Constants.BASE_URL + "AppFile/Version.txt");
        params.setCharset("gbk");
        // 请求
        final Callback.Cancelable request = x.http().get(params,
                new RequestCallBack<ApiResponse>(callback) {
                    @Override
                    public void onSuccess(ApiResponse result) {
                        if (result != null)
                            callback.onSuccessData(result.getVersion());
                       else callback.onSuccessData(null);
                    }
                });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }

    /**
     * 下载文件
     *
     * @param callback
     * @return
     */
    public static RequestHandler downLoad(String path, final IDataResultImpl<File> callback) {
        //http://192.168.1.132:8033/LiHuoService.asmx/
//        RequestParams params = new RequestParams(Constants.BASE_URL+"AppFile/PM10.apk");
        RequestParams params = new RequestParams("http://218.57.204.52:10001/AppFile/PM10.apk");
        params.setAutoResume(true);
        params.setSaveFilePath(path + "PM10.apk");
//        params.setCharset("gbk");

        // 请求
        final Callback.Cancelable request = x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
                callback.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                callback.onLoading(total, current, isDownloading);
            }

            @Override
            public void onSuccess(File result) {
                callback.onSuccessData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) { // 网络错误
                } else if (ex instanceof NullPointerException) {

                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callback.onCancel();
            }

            @Override
            public void onFinished() {
                callback.onFinish();
            }
        });
        // 处理handler
        RequestHandler handler = new RequestHandler() {
            @Override
            public void cancel() {
                request.cancel();
            }
        };
        return handler;
    }
}