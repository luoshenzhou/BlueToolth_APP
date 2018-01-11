package com.haozhi.machinestatu.fengjisystem.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.BaseActionBarActivity;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.AlarmFragment;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.ControlFragment;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.MonitorFragment;

import java.util.ArrayList;
import java.util.List;

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
public class MenuFragmentActivity extends BaseActionBarActivity {

    private static final String TAG = MenuFragmentActivity.class.getSimpleName();

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
    private List<Fragment> mPagerList;
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
                rgGroup.check(rgGroup.getChildAt(arg0).getId());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private void initData() {
        // 初始化5个子页面
        mPagerList = new ArrayList<>();
        mPagerList.add(new ControlFragment());
        mPagerList.add(new MonitorFragment());
        mPagerList.add(new AlarmFragment());
        mViewPager.setAdapter(new ContentAdapter(getSupportFragmentManager(),mPagerList));
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

        //=======加上这两句才会有菜单和箭头的切换效果，否则只会显示箭头
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
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
        mDrawerLayout.openDrawer(Gravity.LEFT);
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

    class ContentAdapter extends FragmentPagerAdapter {

        private List<Fragment> mPagerList;
        public ContentAdapter(FragmentManager fm,List<Fragment> mPagerList) {
            super(fm);
            this.mPagerList=mPagerList;
        }

        @Override
        public Fragment getItem(int position) {
            return mPagerList.get(position);
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }
    }

}
