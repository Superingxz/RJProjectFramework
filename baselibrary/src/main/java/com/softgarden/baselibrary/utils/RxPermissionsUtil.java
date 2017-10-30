package com.softgarden.baselibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.softgarden.baselibrary.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

/**
 * Created by MirkoWu on 2017/4/5 0005.
 */

public class RxPermissionsUtil {

    public static Observable<Boolean> requestPermisson(Activity activity, String... permissions) {
        return new RxPermissions(activity).request(permissions);
    }

    public static Observable<Boolean> shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        return new RxPermissions(activity).shouldShowRequestPermissionRationale(activity, permissions);
    }


    /**
     * 提示缺少什么权限的对话框
     */
    public static void showPermissionDialog(Context context, String title, String message,
                                     DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setPositiveButton(R.string.ok, onClickListener).show();
    }

    /**
     * 提示缺少必要权限对话框
     */
    public static void showLackPermissionDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.tips_message)
                .setMessage(R.string.permission_lack)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setPositiveButton(R.string.ok, (dialog, which) -> startAppSettings(context)).show();
    }

    /**
     * 启动当前应用设置页面
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /***
     * 常用的几个权限申请
     */

    /**
     * 申请相机权限
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestCamera(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.CAMERA);
    }

    /**
     * 申请存储
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestStorage(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 申请定位 粗略
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestLocationCoarse(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    /**
     * 申请定位 精确
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestLocationFine(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.ACCESS_FINE_LOCATION);
    }

}
