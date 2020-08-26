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
public class ToBeInquiryDetailViewModel extends BaseViewModel {
    private String TAG = ToBeInquiryDetailViewModel.class.getSimpleName();
    private IInquiryModel mIInquiryModel;

    public ToBeInquiryDetailViewModel() {

    }

    public ToBeInquiryDetailViewModel(IInquiryModel inquiryModel) {
        mIInquiryModel = inquiryModel;
    }

    public void getInquiryDetailInfo() {

        InquiryModel.getInquiryDetailInfo(new OnLoadListener<OrderListResponse>() {
            @Override
            public void onLoadStart() {

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
        if (mIInquiryModel != null) {
            mIInquiryModel.onRefreshData(list);
        }
    }
}
