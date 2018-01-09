package com.haozhi.machinestatu.fengjisystem.base.base_activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.haozhi.machinestatu.fengjisystem.base.base_application.MyApplication;

import butterknife.ButterKnife;

/**
 * Created by LSZ on 2018/1/4.
 * Activity 基类
 */
public abstract class BaseActivity extends FragmentActivity{

    public String TAG =this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        MyApplication.addActivity(this);
    }

    //获取布局
    public abstract int getLayout();


    //取消网络请求
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        MyApplication.remove(this);
    }
}
