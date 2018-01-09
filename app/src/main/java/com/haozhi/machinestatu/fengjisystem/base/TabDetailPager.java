package com.haozhi.machinestatu.fengjisystem.base;

import android.app.Activity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;

import butterknife.Bind;

/**
 * 页签详情页
 *
 * @author Kevin
 */
public class TabDetailPager extends BaseMenuDetailPager implements
        OnPageChangeListener {


    TextView title;


    String tv_title;


    public TabDetailPager(Activity activity, String tv_title) {
        super(activity);
        this.tv_title=tv_title;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        // 加载头布局
        title= (TextView) view.findViewById(R.id.title);
        title.setText(tv_title);
        return view;
    }


    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {

    }


    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

    }
}
