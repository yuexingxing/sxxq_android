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

    private void loadData() {

        ShoppingCenterResponse shoppingCenterResponse = new ShoppingCenterResponse();
        List<BannerInfo> bannerInfoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BannerInfo bannerInfo = new BannerInfo();
            bannerInfo.artitag_url = Constants.DEFAULT_IMG_URL;
            bannerInfoList.add(bannerInfo);
        }
        shoppingCenterResponse.slideshow = bannerInfoList;
        shoppingCenterResponse.staticAdvertising = shoppingCenterResponse.slideshow.get(0);

        List<GoodsTypeInfo> goodsTypeInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GoodsTypeInfo goodsTypeInfo = new GoodsTypeInfo();
            goodsTypeInfo.title = "90元系列产品";

            List<GoodsDetailInfo> goodsTypeDetailInfoList = new ArrayList<>();
            for (int k = 0; k < 6; k++) {
                GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
                goodsDetailInfo.sartiName = "玻尿酸美容护肤不二之选";
                goodsDetailInfo.sartiMkPrice = 200;
                goodsDetailInfo.sartiSalePrice = 240;
                goodsTypeDetailInfoList.add(goodsDetailInfo);
            }
            goodsTypeInfo.setMealProduct = goodsTypeDetailInfoList;
            goodsTypeInfoList.add(goodsTypeInfo);
        }
        shoppingCenterResponse.classify = goodsTypeInfoList;
        if (mCallBack != null) {
            mCallBack.returnShoppingCenterList(shoppingCenterResponse);
        }
    }
}