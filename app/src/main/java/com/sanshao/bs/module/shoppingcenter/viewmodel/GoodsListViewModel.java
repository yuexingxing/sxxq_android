package com.sanshao.bs.module.shoppingcenter.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsListModel;
import com.sanshao.bs.module.shoppingcenter.model.ShoppingCenterModel;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.LoadDialogMgr;

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
                LoadDialogMgr.getInstance().dismiss();
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
}
