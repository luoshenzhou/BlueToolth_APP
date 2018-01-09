package com.haozhi.machinestatu.fengjisystem.fragmentPager.control;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.TabDetailPager;
import com.haozhi.machinestatu.fengjisystem.bean.TabPagerListViewItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenzhu on 2018/1/8.
 * Control每个小标题的页面
 */
public class ControlTabDetailPager extends TabDetailPager{

    private ListView listView;
    public ControlTabDetailPager(Activity activity, String tv_title) {
        super(activity, tv_title);
    }

    @Override
    public void initData() {
        super.initData();
        List<TabPagerListViewItemModel> list=new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add(new TabPagerListViewItemModel("biaoTi"+i,"danWei"));
        }
        listView.setAdapter(new TabPagerListViewAdapter(list,mActivity));
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.pager_content_layout, null);
        listView = (ListView) view.findViewById(R.id.lv_pagerContent);
        return view;
    }
}
