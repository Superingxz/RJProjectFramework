package com.softgarden.baselibrary.widget;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2016/3/15.
 */
public class LoadingDialogManage {
    private static int mCount = 0;
    private static LoadingDialog mLoadingDialog;

    private LoadingDialogManage() {
    }

    public static void showLoading(Context context) {
        if (mCount == 0) {
            mLoadingDialog = new LoadingDialog(context);
            mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mCount = 0;
                }
            });
            mLoadingDialog.show();
        }
        mCount++;
    }

    public static void dismissLoading() {
        if (mCount == 0) {
            return;
        }

        mCount--;
        if (mCount == 0) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
