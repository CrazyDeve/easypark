package com.benben.carmanager.permission;

public class PermissionUtils {
    private static PermissionUtils sInstance;
    private IPermissionRequestApi permissionManager = new PermissionManager();

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (sInstance == null) {
            sInstance = new PermissionUtils();
        }
        return sInstance;
    }

    public IPermissionRequestApi getPermissionManager() {
        return permissionManager;
    }
}
