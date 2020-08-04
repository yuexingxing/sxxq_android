package com.exam.commonbiz.base;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

public interface BaseViewCallBack {

    LoadingDialog createLoadingDialog();

    LoadingDialog createLoadingDialog(String text);

    boolean visibility();

    boolean viewFinished();
}
