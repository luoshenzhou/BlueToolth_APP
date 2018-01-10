package com.haozhi.machinestatu.fengjisystem.fragmentPager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shenzhu on 2018/1/10.
 */
public class MonitorChildFragmentAdapter extends BaseAdapter {

    private List<MonitorChildFragmentItemModel> list;
    private Activity activity;

    public MonitorChildFragmentAdapter(List<MonitorChildFragmentItemModel> list, Activity activity) {
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
        ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.monitor_pager_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MonitorChildFragmentItemModel itemModel = list.get(position);
        viewHolder.tvKey.setText(itemModel.getName());
        if (itemModel.isStatue()){
            viewHolder.tvDanWei.setText("");
            viewHolder.tvValue.setText("");
        }else {
            viewHolder.tvDanWei.setText(itemModel.getDanwei());
            viewHolder.tvValue.setText(itemModel.getValue());
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_key)
        TextView tvKey;
        @Bind(R.id.tv_value)
        TextView tvValue;
        @Bind(R.id.tv_danWei)
        TextView tvDanWei;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
