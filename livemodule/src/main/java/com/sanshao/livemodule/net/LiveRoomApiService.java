package com.sanshao.livemodule.net;

import com.exam.commonbiz.net.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface LiveRoomApiService {

    //测试接口
    @POST("login")
    Observable<BaseResponse<String>> login(@Query("userid") String userid,
                                           @Query("password") String password);

    //创建直播房间
    @POST("live/mlive/createLive")
    Observable<BaseResponse<String>> createLiveRoom();

    //直播列表
    @GET("live/mlive/batchList")
    Observable<BaseResponse<String>> getLiveRoomList();
}
