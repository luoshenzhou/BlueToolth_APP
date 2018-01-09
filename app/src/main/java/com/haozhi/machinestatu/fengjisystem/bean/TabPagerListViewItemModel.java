package com.haozhi.machinestatu.fengjisystem.bean;

/**
 * Created by shenzhu on 2018/1/8.
 * 每个小标题中listView的item的model
 */
public class TabPagerListViewItemModel {

    public TabPagerListViewItemModel(String title, String danWei) {
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
}
