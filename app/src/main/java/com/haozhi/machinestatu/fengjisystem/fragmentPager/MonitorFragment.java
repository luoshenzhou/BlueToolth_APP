package com.haozhi.machinestatu.fengjisystem.fragmentPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.ControlChildFragment;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.ControlChildFragment1;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.ControlChildFragment2;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.ControlChildFragment3;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.ControlChildFragment4;
import com.haozhi.machinestatu.fengjisystem.view.TopNewsViewPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shenzhu on 2018/1/9.
 */
public class MonitorFragment extends Fragment {

    @Bind(R.id.indicator)
    TabPageIndicator indicator;
    @Bind(R.id.vp_menu_detail)
    TopNewsViewPager vpMenuDetail;
    private List<Fragment> list;
    private String[] title=new String[]{"title0","title1","title2","title3","title4"};
    View rootView;
    private String TAG=this.getClass().getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView==null){
            rootView=inflater.inflate(R.layout.control_pager_layout, null);
        }
        ButterKnife.bind(this, rootView);
        if (list==null){//如果不加这个，那么就会每次重新划回都会冲新建一个list，这样会每次增加5个fragment
            list=new ArrayList<>();
            initData();
        }
        Log.e(TAG, "onCreateView");
        return rootView;
    }


    private void initData() {
        list.add(new MonitorChildFragment("child0"));
        list.add(new ControlChildFragment1("child1"));
        list.add(new ControlChildFragment2("child2"));
        list.add(new ControlChildFragment3("child3"));
        list.add(new ControlChildFragment4("child4"));
        vpMenuDetail.setAdapter(new ControlPagerAdapter(getChildFragmentManager(), list));
        indicator.setViewPager(vpMenuDetail);// 将viewpager和mIndicator关联起来,必须在viewpager设置完adapter后才能调用
        indicator.onPageScrollStateChanged(0);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.onPageScrollStateChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.e(TAG, "onDestroyView");
        ((ViewGroup)rootView.getParent()).removeView(rootView);
    }

    class ControlPagerAdapter extends FragmentStatePagerAdapter{
        @Override
        public Parcelable saveState() {
            return null;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        private List<Fragment> list;
        public ControlPagerAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.list=list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG,"onDetach");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.e(TAG, "onAttachFragment");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        Log.e(TAG, "onDestroyOptionsMenu");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
