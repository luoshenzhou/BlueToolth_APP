package com.haozhi.machinestatu.fengjisystem.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;


/**
 * 主页下5个子页面的基类
 * 
 * @author Kevin
 * 
 */
public class BasePager {

	public Activity mActivity;
	public View mRootView;// 布局对象


	public FrameLayout flContent;// 内容


	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}

	/**
	 * 初始化布局
	 */
	public void initViews() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
	}


	/**
	 * 初始化数据
	 */
	public void initData() {

	}



}
