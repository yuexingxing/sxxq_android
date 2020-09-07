package cn.sanshaoxingqiu.ssbm.module.order.viewmodel;

import android.content.Context;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderDetailModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.OrderModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class OrderDetailViewModel extends BaseViewModel {
    private String TAG = OrderDetailViewModel.class.getSimpleName();

    public IOrderDetailModel mCallBack;

    public void setCallBack(IOrderDetailModel iOrderDetailModel) {
        mCallBack = iOrderDetailModel;
    }

    public void getOrderDetailInfo(Context context, String salebillId) {

        OrderModel.getOrderDetailInfo(salebillId, new OnLoadListener<GoodsDetailInfo>() {

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
                    mCallBack.returnOrderDetailInfo(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void getOrderNumStatus() {

        OrderModel.getOrderNumStatus(new OnLoadListener<OrderNumStatusResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderNumStatusResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderNumStatus(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }


    public void cancelOrder(String salebillId) {

        OrderModel.cancelOrder(salebillId, new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                if (mCallBack != null) {
                    mCallBack.returnCancelOrder();
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

}
