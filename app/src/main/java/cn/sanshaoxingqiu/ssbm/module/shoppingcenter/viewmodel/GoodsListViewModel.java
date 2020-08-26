package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGoodsListModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.ShoppingCenterModel;

import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;

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
