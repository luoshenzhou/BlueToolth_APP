package com.haozhi.machinestatu.fengjisystem.bean;

/**
 * Created by shenzhu on 2018/1/8.
 * 每个小标题中listView的item的model
 */
public class AlarmTabPagerListViewItemModel {

    public AlarmTabPagerListViewItemModel(String title, String danWei) {
        this.title = title;
        this.danWei = danWei;
    }

    //标题
    private String title;
    //功能ID
    private byte functionId;
    //单位
    private String danWei;
    //权限
    private String right;

    private int statue;

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getFunctionId() {
        return functionId;
    }

    public void setFunctionId(byte functionId) {
        this.functionId = functionId;
    }

    public String getDanWei() {
        return danWei;
    }

    public void setDanWei(String danWei) {
        this.danWei = danWei;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TabPagerListViewItemModel{" +
                "title='" + title + '\'' +
                ", functionId=" + functionId +
                ", danWei='" + danWei + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}
