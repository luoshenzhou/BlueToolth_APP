package com.haozhi.machinestatu.fengjisystem.fragmentPager.control;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.bean.TabPagerListViewItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by shenzhu on 2018/1/9.
 */
public class ControlChildFragment extends Fragment {

    @Bind(R.id.tv_undo)
    TextView tvUndo;
    @Bind(R.id.tv_set)
    TextView tvSet;
    @Bind(R.id.tv_read)
    TextView tvRead;
    @Bind(R.id.lv_pagerContent)
    ListView lvPagerContent;

    public ControlChildFragment() {
    }


    private String title;

    public ControlChildFragment(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_content_layout, null);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        List<TabPagerListViewItemModel> list=new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add(new TabPagerListViewItemModel("title"+i,"danwei"));
        }
        lvPagerContent.setAdapter(new TabPagerListViewAdapter(list,getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_undo, R.id.tv_set, R.id.tv_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_undo:
                break;
            case R.id.tv_set:
                break;
            case R.id.tv_read:
                break;
        }
    }

    @OnItemClick(R.id.lv_pagerContent)
    public void onItemClick() {
    }
}
