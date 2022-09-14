package com.benben.carmanager.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.benben.carmanager.R;
import com.benben.carmanager.activity.LoginActivity;
import com.benben.carmanager.model.User;
import com.orhanobut.hawk.Hawk;

public class AuthUtils {

    /**
     * 判断是否登录
     * @return
     */
    public synchronized static boolean hasLogin(){
        return isLogin();
    }



    static boolean isLogin(){
        User user = Hawk.get("user");
        if(user != null){

            if(TextUtils.isEmpty(user.getUsername())){
                return false;
            }
            if(TextUtils.isEmpty(user.getPassword())){
                return false;
            }

            return true;

        }

        return false;
    }


    /**
     * 移除TOKEN
     * @return
     */
    public synchronized static boolean removeToken(){
        Api.TOKEN = "";
        Api.userId=0;
        boolean isSuccess = Hawk.delete(Api.USER_TOKEN_KEY);
        return isSuccess;
    }


    public static void goLogin(Context context) {
        Intent loginIntent = getLoginIntent(context);
        context.startActivity(loginIntent);
        if(context instanceof Activity){
            ((Activity) context).overridePendingTransition(R.anim.login_in,R.anim.login_out);
        }
    }



    private static Intent getLoginIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        if(!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }
}
