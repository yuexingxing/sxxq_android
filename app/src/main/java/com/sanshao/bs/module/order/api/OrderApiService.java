package com.sanshao.bs.module.order.api;

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
 * @time 2020/6/11
 */
public interface OrderApiService {

    //获取订单信息
    @GET("/util/sms/fetch")
    Observable<BaseResponse<ConfirmOrderResponse>> getOrderInfo();

    //提交订单信息
    @POST("/util/sms/fetch")
    Observable<BaseResponse> submitOrderInfo(@Body GoodsDetailInfo goodsDetailInfo);

    //获取订单列表
    @GET("/util/sms/fetch")
    Observable<BaseResponse<OrderListResponse>> getOrderList(@Query("mobile") String mobile,
                                                             @Query("code") String code);

    //获取订单支付信息，发起支付
    @GET("/util/sms/fetch")
    Observable<BaseResponse<OrderListResponse>> getOrderPayInfo(@Query("payType") int payType);

    //获取订单详情
    @GET("/util/sms/fetch")
    Observable<BaseResponse<OrderListResponse>> getOrderDetailInfo(@Query("payType") int payType);
}
