package com.haozhi.machinestatu.fengjisystem.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.BaseActionBarActivity;
import com.haozhi.machinestatu.fengjisystem.base.BasePager;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.AlarmPager;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.ControlPager;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.MonitorPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LSZ on 2017/11/9.
 * 出入仓统计
 * <p/>
 * 待解决：
 * 筛选条件中有null ---OK
 * 选择某些条件的时候出现异常---不能重现
 * 展示不好看-----OK
 * 没有title 没有headview---OK
 * 选中时，是否需要加其他跳转
 * 默认打钩----OK
 * <p/>
 * 输入的数据：
 * jobstatus
 */
public class MenuActivity extends BaseActionBarActivity {

    private static final String TAG = MenuActivity.class.getSimpleName();

    @Bind(R.id.drawerLayout_mainactivity)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.tl_custom)
    Toolbar tlCustom;
    ActionBarDrawerToggle mDrawerToggle;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_news)
    RadioButton rbNews;
    @Bind(R.id.rb_smart)
    RadioButton rbSmart;
    @Bind(R.id.rg_group)
    RadioGroup rgGroup;
    @Bind(R.id.vp_content)
    ViewPager mViewPager;
    private ArrayList<BasePager> mPagerList;
    private String devName="Coiler_ST-2200";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initData();
        initEvent();
    }

    private void initEvent() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                mPagerList.get(arg0).initData();// 获取当前被选中的页面, 初始化该页面数据
                rgGroup.check(rgGroup.getChildAt(arg0).getId());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        mPagerList.get(0).initData();// 初始化首页数据
    }

    private void initData() {
        // 初始化5个子页面
        mPagerList = new ArrayList<>();
        mPagerList.add(new ControlPager(this));
        mPagerList.add(new MonitorPager(this));
        mPagerList.add(new AlarmPager(this));
        mViewPager.setAdapter(new ContentAdapter());
        rgGroup.check(R.id.rb_home);// 默认勾选首页
    }

    private void initToolbar() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, tlCustom, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                openMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerLayout.closeDrawers();
            }
        };

    }


    @Override
    public int getLayout() {
        return R.layout.menu_main_layout;
    }

    @Override
    public Toolbar setToolBar() {
        return tlCustom;
    }

    @Override
    public String getToolBarTitle() {
        return devName;
    }


    //打开侧边栏菜单
    public void openMenu() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.rb_home, R.id.rb_news, R.id.rb_smart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                mViewPager.setCurrentItem(0, false);// 去掉切换页面的动画
                break;
            case R.id.rb_news:
                mViewPager.setCurrentItem(1, false);// 设置当前页面
                break;
            case R.id.rb_smart:
                mViewPager.setCurrentItem(2, false);// 设置当前页面
        }
    }

    class ContentAdapter extends PagerAdapter {

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
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            // pager.initData();// 初始化数据.... 不要放在此处初始化数据, 否则会预加载下一个页面
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}
