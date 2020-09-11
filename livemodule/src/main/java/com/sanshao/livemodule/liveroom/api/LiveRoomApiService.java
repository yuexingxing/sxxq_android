package com.sanshao.livemodule.liveroom.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UploadRoomInfoRequest;
import com.sanshao.livemodule.liveroom.roomutil.bean.GetRoomIdResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.commondef.AnchorInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
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

    @GET("tim/getUserSig")
    Observable<BaseResponse<UserSignResponse>> getUserSig();

    //创建直播房间
    @POST("live/mlive/createLive")
    Observable<BaseResponse> uploadLiveRoomInfo(@Body UploadRoomInfoRequest uploadRoomInfoRequest);

    //直播列表
    @GET("live/mlive/batchList")
    Observable<BaseResponse<String>> getLiveRoomList();

    //获取license
    @GET("live/mlive/license")
    Observable<BaseResponse<LicenceInfo>> getLicense();

    //获取主播信息
    @GET("live/mlive/home")
    Observable<BaseResponse<AnchorInfo>> getAnchorInfo();

    //获取聊天房间号
    @GET("live/mlive/getRoomId")
    Observable<BaseResponse<GetRoomIdResponse>> getRoomId();

    //获取聊天房间号
    @GET("live/mlive/getRoomId")
    Observable<BaseResponse<GetRoomIdResponse>> getBackVideo();
}
