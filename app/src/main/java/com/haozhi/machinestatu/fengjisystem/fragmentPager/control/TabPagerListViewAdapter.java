package com.haozhi.machinestatu.fengjisystem.fragmentPager.control;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.bean.TabPagerListViewItemModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shenzhu on 2018/1/8.
 * 每个小标题中listView的adapter
 */
public class TabPagerListViewAdapter extends BaseAdapter {

    private List<TabPagerListViewItemModel> list;
    private Activity activity;

    public List<TabPagerListViewItemModel> getList() {
        return list;
    }

    public void setList(List<TabPagerListViewItemModel> list) {
        this.list = list;
    }

    public TabPagerListViewAdapter(List<TabPagerListViewItemModel> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.pager_item_layout, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        TabPagerListViewItemModel model = list.get(position);
        viewHolder.tvTitle.setText(model.getTitle());
        viewHolder.tbSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        viewHolder.cbDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.cb_do)
        CheckBox cbDo;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tb_setting)
        ToggleButton tbSetting;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
