package com.exam.commonbiz.util;

import android.content.Context;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

/**
 * loading框
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class LoadDialogMgr {
    private static LoadDialogMgr mLoadDialogMgr;
    private static LoadingDialog mLoadingDialog;

    private LoadDialogMgr() {
    }

    public static LoadDialogMgr getInstance() {
        if (mLoadDialogMgr == null) {
            synchronized (LoadDialogMgr.class) {
                if (mLoadDialogMgr == null) {
                    mLoadDialogMgr = new LoadDialogMgr();
                }
            }
        }
        return mLoadDialogMgr;
    }

    public void show(Context context) {
        mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.setLoadingText("加载中")
                .setSuccessText("")
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)
                .closeSuccessAnim()
//                .setDrawColor(color)
                .setRepeatCount(0)
                .show();
    }

    public void show(Context context, String content) {
        mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.setLoadingText(content)
                .setSuccessText("")
                .setInterceptBack(true)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)
                .closeSuccessAnim()
//                .setDrawColor(color)
                .setRepeatCount(0)
                .show();
    }
    
    public void dismiss() {
        if (mLoadingDialog == null) {
            return;
        }
        mLoadingDialog.close();
        mLoadingDialog = null;
    }
}
