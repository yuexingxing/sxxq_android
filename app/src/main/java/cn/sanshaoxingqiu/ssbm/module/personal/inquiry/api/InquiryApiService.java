package cn.sanshaoxingqiu.ssbm.module.personal.inquiry.api;

import com.exam.commonbiz.net.BaseResponse;

import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Author yuexingxing
 * @time 2020/7/24
 */
public interface InquiryApiService {

    //获取待问诊列表
    @GET("/util/sms/fetch")
    Observable<BaseResponse<OrderListResponse>> getInquiryList();

    //获取待问诊详情
    @GET("/util/sms/fetch")
    Observable<BaseResponse<OrderListResponse>> getInquiryDetailInfo();
}
