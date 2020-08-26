package cn.sanshaoxingqiu.ssbm.module.order.viewmodel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderListResponse;
import cn.sanshaoxingqiu.ssbm.module.order.model.OrderModel;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class OrderListViewModel extends OrderDetailViewModel {

    private String TAG = OrderListViewModel.class.getSimpleName();

    public void getOrderList(int orderState, int page, int pageSize) {
        OrderModel.getOrderList(String.valueOf(orderState), page, pageSize, new OnLoadListener<OrderListResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderListResponse> t) {
                if (mCallBack == null) {
                    return;
                }
                if (page == 1) {
                    mCallBack.onRefreshData(t.getContent());
                } else {
                    mCallBack.onLoadMoreData(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
                if (mCallBack == null) {
                    return;
                }
                if (page == 1) {
                    mCallBack.onRefreshData(null);
                } else {
                    mCallBack.onLoadMoreData(null);
                }
            }
        });
    }
}
