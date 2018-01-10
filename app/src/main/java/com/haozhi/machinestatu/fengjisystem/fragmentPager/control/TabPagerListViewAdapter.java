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

import java.util.ArrayList;
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
public class TabPagerListViewAdapter extends BaseAdapter {
    TabPagerListViewItemModel model ;
    private Map<Integer,TabPagerListViewItemModel> map=new HashMap<>();
    private Map<Integer,Integer> positionList=new HashMap<>();
    //private int currentPosition;
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
            jugeCheckBox(position,viewHolder);
            jugeTogoButton(position,viewHolder);
        }
        model = list.get(position);
        viewHolder.tvTitle.setText(model.getTitle());
        setCheckBoxListener(position,viewHolder);
        setTogoButtonListener(position,viewHolder);

        return convertView;
    }

    private void setTogoButtonListener(final int position,ViewHolder viewHolder) {
        viewHolder.tbSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    positionList.put(position,position);
                }else {
                   positionList.remove(position);
                }
            }
        });
    }

    private void setCheckBoxListener(final int position,ViewHolder viewHolder) {
        viewHolder.cbDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // boolean checked = finalViewHolder.tbSetting.isChecked();
                    //勾选
                    if (isChecked) {
                        map.put(position, model);
                    } else {
                        map.remove(position);
                    }
            }
        });
    }

    private void jugeTogoButton(int position,ViewHolder viewHolder) {
        if (positionList.containsKey(position)){
            viewHolder.tbSetting.setChecked(true);
        }else {
            viewHolder.tbSetting.setChecked(false);
        }
    }

    private void jugeCheckBox(int position,ViewHolder viewHolder) {

        if (map.size()>0){
            if (map.get(position)!=null){
                viewHolder.cbDo.setChecked(true);
            }else {
                viewHolder.cbDo.setChecked(false);
            }
        }else {
            viewHolder.cbDo.setChecked(false);
        }
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


   public Map<Integer,TabPagerListViewItemModel> getCheckedItem(){
       return map;
   }
   public void selectAll(){
       for (int i=0;i<list.size();i++) {
           map.put(i,list.get(i));//全部数据放进map
       }
       notifyDataSetChanged();//刷新ListView
   }
   public void cancelSelect(){
       map=null;
       map=new HashMap<>();
       notifyDataSetChanged();//刷新ListView
   }
}
