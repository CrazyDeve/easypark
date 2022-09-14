package com.benben.carmanager.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.benben.carmanager.fragment.WaitDialogFragment;

public class WaitDialogUtils {
    private static WaitDialogFragment instance;
    private static boolean isShow = false;
    public static boolean isShowing(){
        return isShow;
    }
    static {
        instance = WaitDialogFragment.getInstance();
    }
    public static void show(AppCompatActivity activity){
        isShow = true;
        instance.show(activity.getSupportFragmentManager(),"wait_dialog");
    }

    public static void dismiss(){
        if(isShowing()){
            instance.dismiss();
            isShow = false;
        }
    }

}
