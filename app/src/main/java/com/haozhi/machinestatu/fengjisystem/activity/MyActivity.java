package com.haozhi.machinestatu.fengjisystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.bean.TabPagerListViewItemModel;
import com.haozhi.machinestatu.fengjisystem.fragmentPager.control.TabPagerListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LSZ on 2018/1/10.
 */
public class MyActivity extends Activity {

    @Bind(R.id.tv_undo)
    TextView tvUndo;
    @Bind(R.id.tv_set)
    TextView tvSet;
    @Bind(R.id.tv_read)
    TextView tvRead;
    @Bind(R.id.lv_pagerContent)
    ListView lvPagerContent;
    private TabPagerListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_content_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //setSelectedAllTextView(isSelectedAll);
        List<TabPagerListViewItemModel> list=new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add(new TabPagerListViewItemModel("title"+i,"danwei"));
        }
        if (adapter==null){
            adapter=new TabPagerListViewAdapter(list,this);
        }
        lvPagerContent.setAdapter(adapter);
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


}
