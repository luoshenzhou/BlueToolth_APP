package com.haozhi.machinestatu.fengjisystem.fragmentPager.control;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.BaseMenuDetailPager;
import com.haozhi.machinestatu.fengjisystem.base.TabDetailPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shenzhu on 2018/1/6.
 */
public class ControlDetilsPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {

    TabPageIndicator mIndicator;
    ViewPager mViewPager;

    // private ArrayList<TabDetailPager> mPagerList;

    List<TabDetailPager> mPagerList;

    public ControlDetilsPager(Activity activity) {
        super(activity);
    }

    private List<String> titleList=new ArrayList<>();
    @Override
    public void initData() {
        super.initData();
        for (int i=0;i<15;i++){
            titleList.add("Title"+i);
        }
        mPagerList = new ArrayList<TabDetailPager>();

        // 初始化页签数据
        for (int i = 0; i < titleList.size(); i++) {
            TabDetailPager pager = new ControlTabDetailPager(mActivity,
                    "control"+titleList.get(i));
            mPagerList.add(pager);
        }

        mViewPager.setAdapter(new MenuDetailAdapter());
        mIndicator.setViewPager(mViewPager);// 将viewpager和mIndicator关联起来,必须在viewpager设置完adapter后才能调用
        mIndicator.onPageScrollStateChanged(0);

    }



    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.control_pager_layout, null);
        mIndicator= (TabPageIndicator) view.findViewById(R.id.indicator);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_menu_detail);
        mIndicator.setOnPageChangeListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("ViewPager+++", position + "");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }


    class MenuDetailAdapter extends PagerAdapter {

        /**
         * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "VIEWPGER:" + titleList.get(position));
       // mPagerList.get(position).initData();
       // mIndicator.onPageScrollStateChanged(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
