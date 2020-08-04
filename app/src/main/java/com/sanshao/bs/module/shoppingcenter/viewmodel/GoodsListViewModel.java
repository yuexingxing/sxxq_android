package com.sanshao.bs.module.shoppingcenter.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsListModel;
import com.sanshao.bs.module.shoppingcenter.model.ShoppingCenterModel;
import com.sanshao.bs.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsListViewModel extends BaseViewModel {
    private String TAG = GoodsListViewModel.class.getSimpleName();
    private IGoodsListModel mCallBack;

    public void setCallBack(IGoodsListModel iGoodsListModel) {
        mCallBack = iGoodsListModel;
    }

    public void getGoodsList(String artiTagId, int offset, int limit) {

        ShoppingCenterModel.getGoodsList(artiTagId, offset, limit, new OnLoadListener<List<GoodsDetailInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<List<GoodsDetailInfo>> t) {
                if (mCallBack != null) {
                    if (offset == 0) {
                        mCallBack.onRefreshData(t.getContent());
                    } else {
                        mCallBack.onLoadMoreData(t.getContent());
                    }
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (mCallBack != null) {
                    mCallBack.onNetError();
                }
            }
        });
    }

    private void loadData() {
        List<GoodsDetailInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
            goodsDetailInfo.sarti_name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            goodsDetailInfo.sarti_saleprice = 200;
            goodsDetailInfo.sarti_mkprice = 240;
            goodsDetailInfo.thumbnail_img = Constants.DEFAULT_IMG_URL;
            if (i % 2 == 0) {
                goodsDetailInfo.videoPlayUrl = Constants.VIDEO_PLAY_URL;
            }
            list.add(goodsDetailInfo);
        }
        if (mCallBack != null) {
            mCallBack.onRefreshData(list);
        }
    }
}
