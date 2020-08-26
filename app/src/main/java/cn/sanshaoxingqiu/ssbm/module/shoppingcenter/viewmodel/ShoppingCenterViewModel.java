package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel;

import android.content.Context;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;

import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.ShoppingCenterResponse;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IShoppingCenterModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.ShoppingCenterModel;

import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;

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