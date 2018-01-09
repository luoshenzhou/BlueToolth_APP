package com.haozhi.machinestatu.fengjisystem.base.base_application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.haozhi.machinestatu.fengjisystem.base.base_activity.BaseActivity;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LSZ on 2018/1/4.
 * application 的基类
 */
public class MyApplication extends Application{
    private static final String TAG = MyApplication.class.getSimpleName();
    private static List<Object> activitys = new ArrayList<Object>();
    /**
     * 全局的上下文
     */
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        mContext = getApplicationContext();
    }

    /**
     * 获取context
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 添加activity到容器中
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (!activitys.contains(activity)) {
            activitys.add(activity);
            Log.d(TAG, "add activity :" + activity.getClass().getSimpleName());
        }
    }

    /**
     * 退出App时调用该方法
     * 遍历所有activity并且finish。
     */
    public static void destory() {
        for (Object activity : activitys) {
            ((Activity) activity).finish();
            Log.d(TAG, "destory activity :"+activity.getClass().getSimpleName());
        }
        System.exit(0);
    }

    public static void remove(BaseActivity baseActivity) {
        boolean remove = activitys.remove(baseActivity);
        if (remove){
            Log.d(TAG,"exit activity :"+baseActivity.getClass().getSimpleName());
        }
    }

}
