package com.sanshao.bs.module.order.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.order.bean.AppointmentedInfo;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.CreateOrderRequest;
import com.sanshao.bs.module.order.bean.CreateOrderResponse;
import com.sanshao.bs.module.order.bean.OrderListResponse;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/8/6
 */
public interface AppointmentApiService {

    //获取预约信息列表
    @GET("salebill/getReservationInfo")
    Observable<BaseResponse<AppointmentedInfo>> getAppointmentedList();
}
