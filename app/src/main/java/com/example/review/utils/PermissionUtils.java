package com.example.review.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int WRITE_EXTERNAL_STORAGE_CODE = 100;

    public static boolean isPermissionsReady(Context context, String[] permissions) {
        if (permissions == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public static void reuqestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (permissions == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        List<String> mPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);//添加还未授予的权限
            }
        }
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

}
