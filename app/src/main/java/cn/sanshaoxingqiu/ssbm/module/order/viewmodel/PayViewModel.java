package cn.sanshaoxingqiu.ssbm.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderPayInfoResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.model.IPayModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.PayModel;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class PayViewModel extends BaseViewModel {
    public static int GET_PAY_INFO = 1;
    public static int CHECK_ORDER_STATUS = 2;

    private String TAG = PayViewModel.class.getSimpleName();
    private IPayModel mCallBack;

    public void setCallBack(IPayModel iConfirmPayModel) {
        mCallBack = iConfirmPayModel;
    }

    public void getOrderPayInfo(int optType, String salebillId, String payType) {

        PayModel.getOrderPayInfo(salebillId, payType, new OnLoadListener<OrderPayInfoResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderPayInfoResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderPayInfo(optType, t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void getOrderStatus(String salebillId) {

        PayModel.getOrderStatus(salebillId, new OnLoadListener<OrderStatusResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderStatusResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderStatus(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
