package com.sanshao.bs.module.shoppingcenter.viewmodel;

import android.content.Context;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.shoppingcenter.bean.ShoppingCenterResponse;
import com.sanshao.bs.module.shoppingcenter.model.IShoppingCenterModel;
import com.sanshao.bs.module.shoppingcenter.model.ShoppingCenterModel;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.LoadDialogMgr;

public class ShoppingCenterViewModel extends BaseViewModel {

    public IShoppingCenterModel mCallBack;

    public void setCallBack(IShoppingCenterModel iShoppingCenterModel) {
        mCallBack = iShoppingCenterModel;
    }

    public void getGoodsList(Context context) {

        ShoppingCenterModel.getShoppingCenterList(new OnLoadListener<ShoppingCenterResponse>() {

            @Override
            public void onLoadStart() {
                LoadDialogMgr.getInstance().show(context);
            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<ShoppingCenterResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnShoppingCenterList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (mCallBack != null) {
                    mCallBack.returnShoppingCenterList(null);
                }
            }
        });
    }
}