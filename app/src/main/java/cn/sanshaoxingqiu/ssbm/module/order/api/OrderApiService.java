package cn.sanshaoxingqiu.ssbm.module.order.api;

import com.exam.commonbiz.net.BaseResponse;

import cn.sanshaoxingqiu.ssbm.module.order.bean.ConfirmOrderResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.CreateOrderRequest;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderBenefitResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderListResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

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
    @POST("ssxq/order/create")
    Observable<BaseResponse<GoodsDetailInfo>> createOrderInfo(@Body CreateOrderRequest createOrderRequest);

    //获取订单信息
    @GET("ssxq//util/sms/fetch")
    Observable<BaseResponse<ConfirmOrderResponse>> getOrderInfo();

    //提交订单信息
    @POST("ssxq/order/create")
    Observable<BaseResponse> submitOrderInfo(@Body GoodsDetailInfo goodsDetailInfo);

    //获取订单列表
    @GET("ssxq/salebill/salebillList")
    Observable<BaseResponse<OrderListResponse>> getOrderList(@Query("saleStatus") String saleStatus, @Query("page") int page, @Query("pageSize") int pageSize);

    //获取订单详情
    @GET("ssxq/order/detail")
    Observable<BaseResponse<GoodsDetailInfo>> getOrderDetailInfo(@Query("salebill_id") String salebillId);

    //获取订单数量状态
    @GET("ssxq/salebill/saleStatus")
    Observable<BaseResponse<OrderNumStatusResponse>> getOrderNumStatus();

    //取消订单
    @POST("ssxq/salebill/cancel")
    Observable<BaseResponse> cancelOrder(@Query("salebillId") String salebillId);

    //用户权益订单
    @GET("ssxq/order/benefit")
    Observable<BaseResponse<OrderBenefitResponse>> getOrderBenefit();

    //获取预约信息列表
    @GET("ssxq/salebill/getReservationInfo")
    Observable<BaseResponse<OrderBenefitResponse>> getReservationInfo();
}
