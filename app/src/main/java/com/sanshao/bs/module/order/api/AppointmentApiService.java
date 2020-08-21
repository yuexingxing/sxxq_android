package com.sanshao.bs.module.order.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.order.bean.AppointmentedInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @Author yuexingxing
 * @time 2020/8/6
 */
public interface AppointmentApiService {

    //获取预约信息列表
    @GET("salebill/getReservationInfo")
    Observable<BaseResponse<AppointmentedInfo>> getAppointmentedList();
}
