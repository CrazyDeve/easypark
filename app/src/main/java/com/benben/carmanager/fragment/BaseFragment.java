package com.benben.carmanager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.benben.carmanager.utils.AuthUtils;
import com.google.gson.Gson;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "jyw";
    protected Context context;
    protected Gson gson = new Gson();
    private Toast toast;
    protected LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        toast = Toast.makeText(context,"",Toast.LENGTH_SHORT);

    }


    protected void showToast(String msg){


        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }

    protected void showToast(String msg,int gravity){


        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(gravity,0,0);
            toast.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        return initView();
    }

    protected abstract View initView();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected  void initData(){

    };

    protected void goLogin(){
        AuthUtils.goLogin(context);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: "+getClass().getName()+" : "+hidden);
        if(!hidden){
            initData();
        }
    }


}
