package com.haozhi.machinestatu.fengjisystem.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by LSZ on 2017/11/21.
 * 提示框工具
 */
public class ToastUtil {



    public static void toastShow(String text, Context context) {
        Toast.makeText(context,
                text,
                Toast.LENGTH_SHORT).show();

    }
}
