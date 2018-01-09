package com.haozhi.machinestatu.fengjisystem.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by LSZ on 2017/11/20.
 * 获取APP的assets资源文件的文件内容
 */
public class AssetsUtil {

    public static String getAssetsFileJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bf = null;
        try {
            AssetManager assetManager = context.getAssets();
            bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bf!=null){
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
