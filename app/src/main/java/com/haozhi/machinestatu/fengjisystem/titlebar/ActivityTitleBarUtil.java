package com.haozhi.machinestatu.fengjisystem.titlebar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.haozhi.machinestatu.fengjisystem.R;


/**
 * Created by LSZ on 2017/11/23.
 * Activity状态栏改变
 */
public class ActivityTitleBarUtil {

    private static final String TAG = ActivityTitleBarUtil.class.getSimpleName();

    private Activity context;
    public ActivityTitleBarUtil(Activity context) {
        this.context = context;
    }

    boolean isImmersive = false;//是否使用沉浸模式

    public void initTitle(String titleName) {
        if (hasKitKat() && !hasLollipop()) {
            //用户手机的SDK版本>19&&<21
            Log.e(TAG, "用户手机的SDK版本>19但是<21");
            isImmersive = true;
            //透明状态栏
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            //用户手机的SDK版本>21
            Log.e(TAG, "用户手机的SDK版本>21");
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //                 | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
        }

        final TitleBar titleBar = (TitleBar) context.findViewById(R.id.title_bar);

        titleBar.setImmersive(isImmersive);//设置是否使用沉浸模式

        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));//设置titleBar的颜色

        titleBar.setLeftImageResource(R.mipmap.back_green);//设置titleBar的返回按钮图片
        titleBar.setLeftText("返回");//设置titleBar的返回按钮文字
        titleBar.setLeftTextColor(Color.WHITE);//设置titleBar的返回按钮文字颜色
        titleBar.setLeftClickListener(new View.OnClickListener() {//设置titleBar的返回按钮监听
            @Override
            public void onClick(View v) {
                destory();
            }
        });

        titleBar.setTitle(titleName);//设置titleBar的标题文字
        titleBar.setTitleColor(Color.WHITE);//设置titleBar的标题文字颜色
        titleBar.setSubTitleColor(Color.WHITE);//???
        titleBar.setDividerColor(Color.GRAY);//???
        titleBar.setActionTextColor(Color.WHITE);//???

    }

    public void initTitle(String titleName,TitleBar.TextAction action) {
        if (hasKitKat() && !hasLollipop()) {
            //用户手机的SDK版本>19&&<21
            Log.e(TAG, "用户手机的SDK版本>19但是<21");
            isImmersive = true;
            //透明状态栏
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            //用户手机的SDK版本>21
            Log.e(TAG, "用户手机的SDK版本>21");
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //                 | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
        }

        final TitleBar titleBar = (TitleBar) context.findViewById(R.id.title_bar);

        titleBar.setImmersive(isImmersive);//设置是否使用沉浸模式

        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));//设置titleBar的颜色

        titleBar.setLeftImageResource(R.mipmap.back_green);//设置titleBar的返回按钮图片
        titleBar.setLeftText("返回");//设置titleBar的返回按钮文字
        titleBar.setLeftTextColor(Color.WHITE);//设置titleBar的返回按钮文字颜色
        titleBar.setLeftClickListener(new View.OnClickListener() {//设置titleBar的返回按钮监听
            @Override
            public void onClick(View v) {
                destory();
            }
        });

        titleBar.setTitle(titleName);//设置titleBar的标题文字
        titleBar.setTitleColor(Color.WHITE);//设置titleBar的标题文字颜色
        titleBar.setSubTitleColor(Color.WHITE);//???
        titleBar.setDividerColor(Color.GRAY);//???
        titleBar.setActionTextColor(Color.WHITE);//???
        titleBar.setActionTextColor(Color.WHITE);//???
        titleBar.addAction(action);
    }



    public static boolean hasKitKat() {
        // 用户手机操作系统的SDK版本>=19（Android 4.4）
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    static boolean hasLollipop() {
        // 用户手机操作系统的SDK版本>=21
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void destory(){
        if (context!=null){
            context.finish();
            context=null;
        }
    }

}
