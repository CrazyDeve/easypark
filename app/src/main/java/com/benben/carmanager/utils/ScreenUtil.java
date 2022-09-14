package com.benben.carmanager.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class ScreenUtil {
    public static int getStateBarHeight(Context context) {
        Class c = null;
        int statusBarHeight = 20;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusBarHeight;
    }


    public static int getScreenWidth(Activity context){
        WindowManager windowManager = context.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Activity context){
        WindowManager windowManager = context.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);

        return outMetrics.heightPixels;
    }
}
