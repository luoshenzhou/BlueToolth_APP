package com.haozhi.machinestatu.fengjisystem.fragmentPager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.bean.AlarmTabPagerListViewItemModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shenzhu on 2018/1/8.
 * 每个小标题中listView的adapter
 * 问题：已经记住了状态，但是滑动的时候就是记不住选中的状态,不是activity和fragment的问题
 */
public class AlarmTabPagerListViewAdapter extends BaseAdapter {
    AlarmTabPagerListViewItemModel model;
    private Map<Integer, AlarmTabPagerListViewItemModel> map = new HashMap<>();
    private Map<Integer, Integer> positionList = new HashMap<>();
    //private int currentPosition;
    private List<AlarmTabPagerListViewItemModel> list;
    private Activity activity;

    public List<AlarmTabPagerListViewItemModel> getList() {
        return list;
    }

    public void setList(List<AlarmTabPagerListViewItemModel> list) {
        this.list = list;
    }

    public AlarmTabPagerListViewAdapter(List<AlarmTabPagerListViewItemModel> list, Activity activity) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.alarm_pager_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        jugeCheckBox(position, viewHolder);
        jugeTogoButton(position, viewHolder);
        model = list.get(position);
        viewHolder.tvTitle.setText(model.getTitle());
        viewHolder.imStatue.setImageDrawable(activity.getResources().getDrawable(R.drawable.leak_canary_icon));
        setCheckBoxListener(position, viewHolder);
        setTogoButtonListener(position, viewHolder);

        return convertView;
    }

    private void setTogoButtonListener(final int position, ViewHolder viewHolder) {
        viewHolder.tbSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton toggleButton = (ToggleButton) v;
                if (toggleButton.isChecked()) {
                    positionList.put(position, position);
                } else {
                    positionList.remove(position);
                }
            }
        });
       /* viewHolder.tbSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    positionList.put(position,position);
                }else {
                   positionList.remove(position);
                }
            }
        });*/
    }

    private void setCheckBoxListener(final int position, ViewHolder viewHolder) {
        viewHolder.cbDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox box = (CheckBox) v;
                if (box.isChecked()) {
                    map.put(position, model);
                } else {
                    map.remove(position);
                }
            }
        });

    }

    private void jugeTogoButton(int position, ViewHolder viewHolder) {
        if (positionList.containsKey(position)) {
            viewHolder.tbSetting.setChecked(true);
        } else {
            viewHolder.tbSetting.setChecked(false);
        }
    }

    private void jugeCheckBox(int position, ViewHolder viewHolder) {

        if (map.size() > 0) {
            if (map.get(position) != null) {
                viewHolder.cbDo.setChecked(true);
            } else {
                viewHolder.cbDo.setChecked(false);
            }
        } else {
            viewHolder.cbDo.setChecked(false);
        }
    }




    public Map<Integer, AlarmTabPagerListViewItemModel> getCheckedItem() {
        return map;
    }

    public void selectAll() {
        for (int i = 0; i < list.size(); i++) {
            map.put(i, list.get(i));//全部数据放进map
        }
        notifyDataSetChanged();//刷新ListView
    }

    public void cancelSelect() {
        map = null;
        map = new HashMap<>();
        notifyDataSetChanged();//刷新ListView
    }

    class ViewHolder {
        @Bind(R.id.cb_do)
        CheckBox cbDo;
        @Bind(R.id.im_statue)
        ImageView imStatue;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tb_setting)
        ToggleButton tbSetting;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
