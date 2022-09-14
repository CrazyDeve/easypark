package com.benben.carmanager.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public abstract class BaseActivity extends AppCompatActivity {

    protected Gson gson = new Gson();
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTextColor(true);
        initView();
        initData();

        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化视图
     */
    protected abstract void initView();


    /**
     *      * 通过设置全屏，设置状态栏透明
     * <p>
     *      * @param blackStatusBarText 状态栏系统字体图标是否为黑色
     * <p>
     *      * @url https://blog.csdn.net/brian512/article/details/52096445
     * <p>
     *     
     */

    protected void setStatusBarTextColor(boolean isBlackColor){
        if(isBlackColor){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                Window window = getWindow();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    View decorView = window.getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }

    }

    protected void fullScreen(Activity activity, boolean blackStatusBarText) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色

                Window window = activity.getWindow();

                View decorView = window.getDecorView();

                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间

                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

                //在6.0增加了View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR，这个字段就是把状态栏标记为浅色，然后状态栏的字体颜色自动转换为深色

                if (blackStatusBarText) {

                    option = option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

                }

                decorView.setSystemUiVisibility(option);

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                window.setStatusBarColor(Color.TRANSPARENT);

                //导航栏颜色也可以正常设置

//                window.setNavigationBarColor(Color.TRANSPARENT);

            } else {

                Window window = activity.getWindow();

                WindowManager.LayoutParams attributes = window.getAttributes();

                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

                attributes.flags |= flagTranslucentStatus;

//                attributes.flags |= flagTranslucentNavigation;

                window.setAttributes(attributes);

            }

        }

    }


    /**
     * 判断字符串是否为非空
     * @param str
     * @return
     */
    protected boolean isNoEmptyString(String str){
        return !TextUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    protected boolean isEmptyString(String str){
        return TextUtils.isEmpty(str);
    }

    protected void showToast(String msg){
        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }

    protected void showToast(String msg,int grivaty){
        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(grivaty,0,0);
            toast.show();
        }
    }

}
