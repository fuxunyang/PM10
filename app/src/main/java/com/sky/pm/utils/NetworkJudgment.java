package com.sky.pm.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import cn.bmob.v3.Bmob;

/**
 * @author libin
 * @ClassName: NetworkJudgment
 * @Description: TODO 网络判断
 * @date 2014年12月19日 上午11:16:29
 */
public class NetworkJudgment {
    /**
     * 一、抓取所有的网络连接方式，判断是否可用,有一个链接则返回true。不推荐
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断网络是否连接，推荐
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        Bmob.initialize(context, "912eeae77d61f9f34c809772eb299b6c");
        ProjectManager.select(context);

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
//			isAvailable()|| isConnectedOrConnecting()|| info.getState() == NetworkInfo.State.CONNECTED
            return null != info && info.isConnectedOrConnecting();
        }
        return false;
    }


    /**
     * 二、判断GPS是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        return ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
//        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
//        List<String> accessibleProviders = lm.getProviders(true);
//        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * 判断基站定位是否开启，移动位置服务（LBS——Location Based Service）network
     *
     * @param context
     * @return
     */
    public static boolean isLBSEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * 三、判断WIFI是否打开
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) ||
                mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 四、判断是否是3G网络
     *
     * @param context
     * @return
     */
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 五、判断是wifi还是手机网络,用户的体现性在这里了，wifi就可以建议下载或者在线播放。
     *
     * @param context
     * @return wifi=true;
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
//        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
//            return true;
//        }
//        return false;
    }
}
