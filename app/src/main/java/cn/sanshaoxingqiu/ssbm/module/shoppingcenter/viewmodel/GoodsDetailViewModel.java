package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel;

import android.content.Context;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGoodsDetailModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.ShoppingCenterModel;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

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

    public void getGoodsDetail(Context context, String sartiId) {

        ShoppingCenterModel.getGoodsDetail(sartiId, new OnLoadListener<GoodsDetailInfo>() {

            @Override
            public void onLoadStart() {
                LoadDialogMgr.getInstance().show(context);
            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<GoodsDetailInfo> t) {
                if (mCallBack != null) {
                    mCallBack.returnGoodsDetail(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
                if (mCallBack != null) {
                    mCallBack.returnGoodsDetail(null);
                }
            }
        });
    }
}
