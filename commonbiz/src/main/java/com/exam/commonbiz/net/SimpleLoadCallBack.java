package com.exam.commonbiz.net;

import com.exam.commonbiz.base.BaseViewCallBack;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

public abstract class SimpleLoadCallBack<T> implements OnLoadListener<T>{

    private BaseViewCallBack callBack;

    public SimpleLoadCallBack(BaseViewCallBack callBack) {
        this.callBack = callBack;
    }

    LoadingDialog dialog = null;

    @Override
    public void onLoadStart() {
        if (callBack.viewFinished()) {
            return;
        }
        dialog = createLoadingDialog();
        dialog.show();
    }

    @Override
    public void onLoadCompleted() {
        if (callBack.viewFinished()) {
            return;
        }
        if (dialog != null) {
            dialog.close();
        }
    }

    protected LoadingDialog createLoadingDialog(){
        if (callBack != null) {
            return callBack.createLoadingDialog();
        }
        return null;
    }

}
