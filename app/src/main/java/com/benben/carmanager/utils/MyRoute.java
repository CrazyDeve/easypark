package com.benben.carmanager.utils;

import android.content.Context;

public class MyRoute {
    public static void go(Context context, String url) {
        if(!AuthUtils.hasLogin()){
            AuthUtils.goLogin(context);
            return;
        }


    }
}
