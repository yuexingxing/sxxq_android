package com.sanshao.bs.module.shoppingcenter.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.ResponseGoodsDetail;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsDetailModel;
import com.sanshao.bs.module.shoppingcenter.model.ShoppingCenterModel;
import com.sanshao.bs.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsDetailViewModel extends BaseViewModel {
    private String TAG = GoodsDetailViewModel.class.getSimpleName();
    private IGoodsDetailModel mCallBack;

    public void setCallBack(IGoodsDetailModel iGoodsDetailModel) {
        mCallBack = iGoodsDetailModel;
    }

    public void getGoodsDetail(String sartiId) {

        ShoppingCenterModel.getGoodsDetail(sartiId, new OnLoadListener<GoodsDetailInfo>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<GoodsDetailInfo> t) {
                if (mCallBack != null) {
                    mCallBack.returnGoodsDetail(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
