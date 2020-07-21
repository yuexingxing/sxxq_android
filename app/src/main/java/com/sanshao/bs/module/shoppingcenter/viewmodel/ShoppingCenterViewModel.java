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

    public void getGoodsList(IShoppingCenterModel callback){

        ShoppingCenterModel.getShoppingCenterList(new OnLoadListener<ShoppingCenterResponse>() {

            @Override
            public void onLoadStart() {
                loadData(callback);
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<ShoppingCenterResponse> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    private void loadData(IShoppingCenterModel callback) {

        ShoppingCenterResponse shoppingCenterResponse = new ShoppingCenterResponse();
        List<BannerInfo> bannerInfoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BannerInfo bannerInfo = new BannerInfo();
            bannerInfo.image = Constants.DEFAULT_IMG_URL;
            bannerInfoList.add(bannerInfo);
        }
        shoppingCenterResponse.banner = bannerInfoList;
        shoppingCenterResponse.ad = Constants.DEFAULT_IMG_URL;

        List<GoodsTypeInfo> goodsTypeInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GoodsTypeInfo goodsTypeInfo = new GoodsTypeInfo();
            goodsTypeInfo.title = "90元系列产品";

            List<GoodsDetailInfo> goodsTypeDetailInfoList = new ArrayList<>();
            for (int k = 0; k < 6; k++) {
                GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
                goodsDetailInfo.name = "玻尿酸美容护肤不二之选";
                goodsDetailInfo.price = 200;
                goodsDetailInfo.oldPrice = 240;
                goodsTypeDetailInfoList.add(goodsDetailInfo);
            }
            goodsTypeInfo.goodsTypeDetailInfoList = goodsTypeDetailInfoList;
            goodsTypeInfoList.add(goodsTypeInfo);
        }
        shoppingCenterResponse.goodsTypeInfoList = goodsTypeInfoList;
        if (callback != null){
            callback.returnShoppingCenterList(shoppingCenterResponse);
        }
    }
}