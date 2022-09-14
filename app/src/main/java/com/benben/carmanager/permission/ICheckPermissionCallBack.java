package com.benben.carmanager.permission;

public interface ICheckPermissionCallBack {

    //所有权限申请通过
    void onPermissionGranted();

    //部分权限没通过
    void onPermissionDenied(String[] deniedPermissions);


}
