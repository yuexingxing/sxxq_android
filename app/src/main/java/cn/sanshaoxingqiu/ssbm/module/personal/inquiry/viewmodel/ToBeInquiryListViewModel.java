package cn.sanshaoxingqiu.ssbm.module.personal.inquiry.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderListResponse;
import cn.sanshaoxingqiu.ssbm.module.personal.inquiry.model.IInquiryModel;
import cn.sanshaoxingqiu.ssbm.module.personal.inquiry.model.InquiryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/7/24
 */
public class ToBeInquiryListViewModel extends BaseViewModel {
    private String TAG = ToBeInquiryListViewModel.class.getSimpleName();
    private IInquiryModel mCallBack;

    public void setCallBack(IInquiryModel inquiryModel) {
        mCallBack = inquiryModel;
    }

    public void getInquiryList() {

        InquiryModel.getInquiryList(new OnLoadListener<OrderListResponse>() {
            @Override
            public void onLoadStart() {
                loadData();
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderListResponse> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    private void loadData() {
        List<OrderInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = "乔丽尔医美";
            list.add(orderInfo);
        }
        if (mCallBack != null) {
            mCallBack.onRefreshData(list);
        }
    }
}
