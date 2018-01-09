package com.haozhi.machinestatu.fengjisystem.log;

import android.util.Log;

/**
 * Created by LSZ on 2017/12/27.
 * 日志管理
 * APP_NAME:是为了拿来进行log过滤的
 * TAG:则是用来区分项目中哪一个class的
 */
public class LogManager {

    //是否打印log
    private static boolean isDebug = true;
    //这里的APP_NAME是为了拿来进行log过滤的
    private static String APP_NAME = "mytag-";

    public static void v(String TAG, String deBugMsg) {
        if (isDebug) {
            Log.v(APP_NAME+TAG, deBugMsg);
        }
    }

    public static void d(String TAG, String deBugMsg) {
        if (isDebug) {
            Log.d(APP_NAME+TAG, deBugMsg);
        }
    }

    public static void i(String TAG, String deBugMsg) {
        if (isDebug) {
            Log.i(APP_NAME+TAG, deBugMsg);
        }
    }

    public static void w(String TAG, String deBugMsg) {
        if (isDebug) {
            Log.w(APP_NAME+TAG, deBugMsg);
        }
    }

    public static void e(String TAG, String deBugMsg) {
        if (isDebug) {
            Log.e(APP_NAME+TAG, deBugMsg);
        }
    }

}
