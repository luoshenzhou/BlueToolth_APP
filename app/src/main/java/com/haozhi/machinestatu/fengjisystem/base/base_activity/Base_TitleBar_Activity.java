package com.haozhi.machinestatu.fengjisystem.base.base_activity;

import android.os.Bundle;
import android.text.TextUtils;


import com.haozhi.machinestatu.fengjisystem.titlebar.ActivityTitleBarUtil;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;

import butterknife.ButterKnife;

/**
 * Created by LSZ on 2017/12/28.
 * 有titlebar和使用butterKnife的activity基类
 */
public abstract class Base_TitleBar_Activity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initTitleBar();
    }



    //标题的名字
    protected abstract String setTitleName();

    //标题右侧按钮
    protected abstract TitleBar.TextAction rightButton();

    private void initTitleBar() {
        ActivityTitleBarUtil titleBar = new ActivityTitleBarUtil(this);
        String titleName = setTitleName();
        if (TextUtils.isEmpty(titleName)) {
            titleName="";
        }
        if (rightButton()==null){
            titleBar.initTitle(titleName);
        }else {
            titleBar.initTitle(titleName, rightButton());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
