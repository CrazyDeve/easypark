package com.benben.carmanager.permission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.benben.carmanager.BuildConfig;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager implements IPermissionRequestApi {
    private static final String TAG = "jyw";

    @Override
    public void checkPermissions(Context context, String[] permissions, ICheckPermissionCallBack callBack) {
        if (permissions == null || permissions.length == 0) {
            callBack.onPermissionGranted();
            return;
        }

        List<String> deniedPermission = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                deniedPermission.add(permissions[i]);
            }
        }

        if (deniedPermission != null && deniedPermission.size() > 0) {
            callBack.onPermissionDenied(deniedPermission.toArray(new String[deniedPermission.size()]));
        } else {
            callBack.onPermissionGranted();
            return;
        }

    }

    @Override
    public void requestPermissions(Activity activity, String[] deniedPermissions, int reqPermissionCode) {
        if (deniedPermissions == null || deniedPermissions.length == 0) {
            throw new RuntimeException("权限不能为空");
        }
        ActivityCompat.requestPermissions(activity, deniedPermissions, reqPermissionCode);
    }

    @Override
    public void requestManualySetPerm(Context context) throws Exception{
        Log.d(TAG, "requestManualySetPerm: 当前设备生产商 == " + Build.MANUFACTURER);
        Intent intent = new Intent();
        switch (Build.MANUFACTURER) {
            case "Xiaomi":
                ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                intent.setComponent(componentName);
                intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
                context.startActivity(intent);
                break;
            default:
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("跳转界面失败...");
                }
                break;
        }

    }

}
