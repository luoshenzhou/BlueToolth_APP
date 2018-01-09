package com.haozhi.machinestatu.fengjisystem.fragmentPager;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.activity.MenuActivity;
import com.haozhi.machinestatu.fengjisystem.base.BaseMenuDetailPager;
import com.haozhi.machinestatu.fengjisystem.base.BasePager;
import com.haozhi.machinestatu.fengjisystem.base.TabDetailPager;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.ControlDetilsPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by shenzhu on 2018/1/6.
 */
public class ControlPager extends BasePager  {

    private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合
    public ControlPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        // 准备4个菜单详情页
        mPagers = new ArrayList<BaseMenuDetailPager>();
        mPagers.add(new ControlDetilsPager(mActivity));
        setCurrentMenuDetailPager(0);// 设置菜单详情页-新闻为默认当前页
    }


    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
        flContent.removeAllViews();// 清除之前的布局
        flContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局
        pager.initData();
    }
}
