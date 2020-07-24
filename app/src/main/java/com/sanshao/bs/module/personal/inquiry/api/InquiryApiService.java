package com.sanshao.bs.module.personal.inquiry.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.OrderListResponse;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/7/24
 */
public interface InquiryApiService {

    //获取订单列表
    @GET("/util/sms/fetch")
    Observable<BaseResponse<OrderListResponse>> getInquiryList();

    //获取订单详情
    @GET("/util/sms/fetch")
    Observable<BaseResponse<OrderListResponse>> getInquiryDetailInfo();
}
