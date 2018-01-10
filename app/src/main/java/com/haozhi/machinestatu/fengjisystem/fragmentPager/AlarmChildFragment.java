package com.haozhi.machinestatu.fengjisystem.fragmentPager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.bean.AlarmTabPagerListViewItemModel;
import com.haozhi.machinestatu.fengjisystem.bean.TabPagerListViewItemModel;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.TabPagerListViewAdapter;
import com.haozhi.machinestatu.fengjisystem.log.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by shenzhu on 2018/1/9.
 */
public class AlarmChildFragment extends Fragment {

    private static final String TAG = AlarmChildFragment.class.getSimpleName();
    @Bind(R.id.tv_undo)
    TextView tvUndo;
    @Bind(R.id.tv_set)
    TextView tvSet;
    @Bind(R.id.tv_read)
    TextView tvRead;
    @Bind(R.id.lv_pagerContent)
    ListView lvPagerContent;
    private AlarmTabPagerListViewAdapter adapter;

    public AlarmChildFragment() {
    }


    private String title;

    public AlarmChildFragment(String title) {
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
        setSelectedAllTextView(isSelectedAll);
        List<AlarmTabPagerListViewItemModel> list=new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add(new AlarmTabPagerListViewItemModel("title"+i,"danwei"));
        }
        if (adapter==null){
            adapter=new AlarmTabPagerListViewAdapter(list,getActivity());
        }
        lvPagerContent.setAdapter(adapter);
    }

    public void setSelectedAllTextView(boolean isSelectedAll){
        if (!isSelectedAll){
            tvUndo.setText("SELECT ALL");
        }else {
            tvUndo.setText("UNDO");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private boolean isSelectedAll=false;
    @OnClick({R.id.tv_undo, R.id.tv_set, R.id.tv_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_undo:
                if (!isSelectedAll){
                    adapter.selectAll();
                    Map<Integer, AlarmTabPagerListViewItemModel> checkedAll = adapter.getCheckedItem();
                    printChecked(checkedAll);
                    isSelectedAll=true;
                }else {
                    adapter.cancelSelect();
                    isSelectedAll=false;
                }
                setSelectedAllTextView(isSelectedAll);
                break;
            case R.id.tv_set:
                Map<Integer, AlarmTabPagerListViewItemModel> checkedSetting = adapter.getCheckedItem();
                printChecked(checkedSetting);
                break;
            case R.id.tv_read:
                Map<Integer, AlarmTabPagerListViewItemModel> checkedRead = adapter.getCheckedItem();
                printChecked(checkedRead);
                break;
        }
    }


    private void printChecked(Map<Integer, AlarmTabPagerListViewItemModel> map){
        for (Map.Entry<Integer,AlarmTabPagerListViewItemModel> entry:map.entrySet()){
            AlarmTabPagerListViewItemModel model = entry.getValue();
            Integer position = entry.getKey();
            LogManager.e(TAG,"POSITION:"+position+model.toString());
        }
    }

    @OnItemClick(R.id.lv_pagerContent)
    public void onItemClick() {
    }
}
