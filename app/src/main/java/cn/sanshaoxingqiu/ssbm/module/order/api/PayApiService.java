package cn.sanshaoxingqiu.ssbm.module.order.api;

import com.exam.commonbiz.net.BaseResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderPayInfoResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderStatusResponse;

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

    //支付90元免费权益身份接口
    @GET("w/pay/fvipPay")
    Observable<BaseResponse<OrderPayInfoResponse>> fVipPay(@Query("pay_type") String payType);
}
