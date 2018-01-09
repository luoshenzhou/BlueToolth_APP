package com.haozhi.machinestatu.fengjisystem.fragmentPager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.BasePager;
import com.haozhi.machinestatu.fengjisystem.base.base_fragment.BaseFragment;

/**
 * Created by shenzhu on 2018/1/6.
 */
public class MonitorPager extends BasePager {
    public MonitorPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        TextView text = new TextView(mActivity);
        text.setText("monitor");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向FrameLayout中动态添加布局
        flContent.addView(text);
    }
}
