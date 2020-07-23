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

    public GoodsDetailViewModel(){

    }

    public GoodsDetailViewModel(IGoodsDetailModel iGoodsDetailModel) {
        mCallBack = iGoodsDetailModel;
    }

    public void getGoodsList() {

        ShoppingCenterModel.getGoodsList("", "", new OnLoadListener<List<GoodsDetailInfo>>() {

            @Override
            public void onLoadStart() {
                loadData();
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<List<GoodsDetailInfo>> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    private void loadData() {
        ResponseGoodsDetail responseGoodsDetail = new ResponseGoodsDetail();

        GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
        goodsDetailInfo.name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
        goodsDetailInfo.price = 200;
        goodsDetailInfo.oldPrice = 240;
        goodsDetailInfo.icon = Constants.DEFAULT_IMG_URL;
        goodsDetailInfo.videoPlayUrl = Constants.VIDEO_PLAY_URL;
        goodsDetailInfo.instruction = "玻尿酸（Hyaluronan）是由D-葡萄糖醛酸及N-乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构双糖单位又称糖醛酸、透明质酸基本结构。乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类。";
        responseGoodsDetail.goodsDetailInfo = goodsDetailInfo;

        List<GoodsDetailInfo> goodsDetailInfoList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            GoodsDetailInfo goodsTypeDetailInfo = GoodsDetailInfo.getGoodsDetailInfo();
            goodsTypeDetailInfo.name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            goodsDetailInfoList.add(goodsTypeDetailInfo);
        }
        responseGoodsDetail.setMealList = goodsDetailInfoList;

        if (mCallBack != null) {
            mCallBack.returnGoodsDetail(responseGoodsDetail);
        }
    }
}
