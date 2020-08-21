package com.sanshao.bs.module.order.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;
import com.sanshao.bs.module.order.bean.OrderStatusResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/8/6
 */
public interface PayApiService {

    //获取订单支付信息，发起支付
    @GET("w/pay/pay")
    Observable<BaseResponse<OrderPayInfoResponse>> getOrderPayInfo(@Query("salebill_id") String salebill_id,
                                                                   @Query("pay_type") String payType
    );

    //确认订单付款状态
    @GET("orderStatus")
    Observable<BaseResponse<OrderStatusResponse>> getOrderStatus(@Query("salebillId") String salebillId);
}
