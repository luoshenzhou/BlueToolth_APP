package com.haozhi.machinestatu.fengjisystem.fragmentPager;

/**
 * Created by shenzhu on 2018/1/10.
 * moitor fragment的子frgment的适配器
 */
public class MonitorChildFragmentItemModel {

    public MonitorChildFragmentItemModel(String name, String value, boolean statue, String danwei) {
        this.name = name;
        this.value = value;
        this.statue = statue;
        this.danwei = danwei;
    }

    public MonitorChildFragmentItemModel() {
    }

    //参数名字
    private String name;

    private String value;

    private boolean statue;

    private String danwei;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStatue() {
        return statue;
    }

    public void setStatue(boolean statue) {
        this.statue = statue;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }
}
