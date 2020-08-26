package com.sanshao.bs.module.order.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.CreateOrderRequest;
import com.sanshao.bs.module.order.bean.OrderBenefitResponse;
import com.sanshao.bs.module.order.bean.OrderListResponse;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;
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

    //创建订单信息
    @POST("order/create")
    Observable<BaseResponse<GoodsDetailInfo>> createOrderInfo(@Body CreateOrderRequest createOrderRequest);

    //获取订单信息
    @GET("/util/sms/fetch")
    Observable<BaseResponse<ConfirmOrderResponse>> getOrderInfo();

    //提交订单信息
    @POST("order/create")
    Observable<BaseResponse> submitOrderInfo(@Body GoodsDetailInfo goodsDetailInfo);

    //获取订单列表
    @GET("salebill/salebillList")
    Observable<BaseResponse<OrderListResponse>> getOrderList(@Query("saleStatus") String saleStatus, @Query("page") int page, @Query("pageSize") int pageSize);

    //获取订单详情
    @GET("order/detail")
    Observable<BaseResponse<GoodsDetailInfo>> getOrderDetailInfo(@Query("salebill_id") String salebillId);

    //获取订单数量状态
    @GET("salebill/saleStatus")
    Observable<BaseResponse<OrderNumStatusResponse>> getOrderNumStatus();

    //取消订单
    @POST("salebill/cancel")
    Observable<BaseResponse> cancelOrder(@Query("salebillId") String salebillId);

    //用户权益订单
    @GET("order/benefit")
    Observable<BaseResponse<OrderBenefitResponse>> getOrderBenefit();
}
