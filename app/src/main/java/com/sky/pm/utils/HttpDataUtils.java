package com.sky.pm.utils;

import com.google.gson.Gson;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.api.RequestCallBack;
import com.sky.pm.common.Constants;
import com.sky.pm.model.ApiResponse;
import com.sky.pm.model.Latest;
import com.sky.pm.model.NewsModel;
import com.sky.pm.model.User;
import com.sky.pm.model.WeatherEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
    public static void login(String name, String pass, final IDataResultImpl<User> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/ManagerLogin");
        params.addBodyParameter("userName", name);
        params.addBodyParameter("passWord", pass);
        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<ApiResponse<List<User>>>(callback) {
            @Override
            public void onSuccess(ApiResponse<List<User>> result) {
                if (result != null) callback.onSuccessData(result.getRows().get(0));
                else callback.onSuccessData(null);
            }
        });
    }

    /**
     * 厂区监测 AQI指数
     *
     * @param callback
     */
    public static void DMS_T_DATA_LATESTGetAllListByJson(final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_LATESTService.asmx/DMS_T_DATA_LATESTGetAllListByJson");
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
     * 1小时
     *
     * @param callback
     */
    public static void DMS_T_DATA_MINUTEGetListByPageByJson(String id, final IDataResultImpl<List<Latest>> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "DMS_T_DATA_MINUTEService.asmx/DMS_T_DATA_MINUTEGetListByPageByJson");
        params.addBodyParameter("json", "[{\"Compare\":\"=\",\"FieldName\":\"StationId\",\"FieldValue\":\""+id+"\",\"IsLike\":false}]");
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
        params.addBodyParameter("json", "[{\"Compare\":\"=\",\"FieldName\":\"StationId\",\"FieldValue\":\""+id+"\",\"IsLike\":false}]");
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
     * 站点管理
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
     * 注册
     *
     * @param TrueName 姓名
     * @param UserID   身份证号
     * @param UserName 昵称
     * @param Pwd      密码
     * @param AreaID   区域id
     * @param PHNo     手机
     * @param callback
     */
    public static void tbAppUsersAdd(String TrueName, String UserID, String UserName,
                                     String Pwd, String AreaID, String PHNo,
                                     final IDataResultImpl<String> callback) {
        RequestParams params = new RequestParams(Constants.BASE_URL + "/tbAppUsersAdd");
        params.addBodyParameter("TrueName", TrueName);
        params.addBodyParameter("UserID", UserID);
        params.addBodyParameter("UserName", UserName);
        params.addBodyParameter("Pwd", Pwd);
        params.addBodyParameter("AreaID", AreaID);
        params.addBodyParameter("PHNo", PHNo);
//        params.setCharset("gbk");
        x.http().post(params, new RequestCallBack<String>(callback) {

            @Override
            public void onSuccess(String result) {
                if (result != null) callback.onSuccessData(result);
                else callback.onSuccessData(null);
            }
        });
    }

}