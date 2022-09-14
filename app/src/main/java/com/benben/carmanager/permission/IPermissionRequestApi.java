package com.benben.carmanager.permission;

import android.app.Activity;
import android.content.Context;

public interface IPermissionRequestApi {
    /**
     * 检查一组多个权限
     * @param permissions
     */
    void checkPermissions(Context context,String [] permissions, ICheckPermissionCallBack callBack);

    /**
     * 申请权限
     * @param activity
     * @param deniedPermissions
     * @param reqPermissionCode
     */
    void requestPermissions(Activity activity, String[] deniedPermissions, int reqPermissionCode);


    /**
     * 请求手动设置权限
     */
    void requestManualySetPerm(Context context) throws Exception;
}
