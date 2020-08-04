package com.exam.commonbiz.util;

import android.content.Context;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

public class LoadingDialogUtil {

    public static LoadingDialog createLoadingDialog(Context context) {
        LoadingDialog mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.setLoadingText("加载中")
                .setSuccessText("")
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)
                .closeSuccessAnim()
                .setRepeatCount(0);
        return mLoadingDialog;
    }

    public static LoadingDialog createLoadingDialog(Context context, String text) {
        LoadingDialog mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.setLoadingText(text)
                .setSuccessText("")
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)
                .closeSuccessAnim()
                .setRepeatCount(0);
        return mLoadingDialog;
    }
}
