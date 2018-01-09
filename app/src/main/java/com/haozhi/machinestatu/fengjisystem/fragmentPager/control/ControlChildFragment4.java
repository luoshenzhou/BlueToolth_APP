package com.haozhi.machinestatu.fengjisystem.fragmentPager.control;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shenzhu on 2018/1/9.
 */
public class ControlChildFragment4 extends Fragment {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    private String title;

    public ControlChildFragment4(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.control_child_fragment_layout, null);
        ButterKnife.bind(this, view);
        tvTitle.setText(title);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
