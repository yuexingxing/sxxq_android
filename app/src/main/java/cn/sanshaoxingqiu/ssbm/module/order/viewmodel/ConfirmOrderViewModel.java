package cn.sanshaoxingqiu.ssbm.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.order.bean.ConfirmOrderResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.CreateOrderRequest;
import cn.sanshaoxingqiu.ssbm.module.order.model.IConfirmOrderModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.OrderModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import com.exam.commonbiz.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class ConfirmOrderViewModel extends BaseViewModel {
    private String TAG = ConfirmOrderViewModel.class.getSimpleName();
    private IConfirmOrderModel mCallBack;

    public void setCallBack(IConfirmOrderModel iConfirmOrderModel) {
        mCallBack = iConfirmOrderModel;
    }

    public void createOrderInfo(CreateOrderRequest createOrderRequest) {

        OrderModel.createOrderInfo(createOrderRequest, new OnLoadListener<GoodsDetailInfo>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<GoodsDetailInfo> t) {
                if (mCallBack != null) {
                    mCallBack.returnCreateOrderInfo(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void getOrderInfo() {

        OrderModel.getOrderInfo(new OnLoadListener<ConfirmOrderResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<ConfirmOrderResponse> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    public void submitOrderInfo(GoodsDetailInfo goodsDetailInfo) {

        OrderModel.submitOrderInfo(goodsDetailInfo, new OnLoadListener<ConfirmOrderResponse>() {

            @Override
            public void onLoadStart() {
                if (mCallBack != null) {
                    mCallBack.returnSubmitOrderInfo();
                }
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
