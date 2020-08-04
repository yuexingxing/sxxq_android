package com.sanshao.bs.module.shoppingcenter.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.home.model.BannerInfo;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsTypeInfo;
import com.sanshao.bs.module.shoppingcenter.bean.ShoppingCenterResponse;
import com.sanshao.bs.module.shoppingcenter.model.IShoppingCenterModel;
import com.sanshao.bs.module.shoppingcenter.model.ShoppingCenterModel;
import com.sanshao.bs.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCenterViewModel extends BaseViewModel {

    public IShoppingCenterModel mCallBack;

    public void setCallBack(IShoppingCenterModel iShoppingCenterModel) {
        mCallBack = iShoppingCenterModel;
    }

    public void getGoodsList() {

        ShoppingCenterModel.getShoppingCenterList(new OnLoadListener<ShoppingCenterResponse>() {

            @Override
            public void onLoadStart() {
//                loadData();
            }

            @Override
            public void onLoadCompleted() {

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