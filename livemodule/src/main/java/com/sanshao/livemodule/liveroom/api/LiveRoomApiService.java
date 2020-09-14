package com.sanshao.livemodule.liveroom.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.commondef.AnchorInfo;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

import java.util.List;

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

    @GET("tim/getUserSig")
    Observable<BaseResponse<UserSignResponse>> getUserSig();

    //获取license
    @GET("live/mlive/license")
    Observable<BaseResponse<LicenceInfo>> getLicense();

    //获取主播信息
    @GET("live/mlive/home")
    Observable<BaseResponse<AnchorInfo>> getAnchorInfo();

    //直播回放列表
    @GET("live/mlive/video/list")
    Observable<BaseResponse<List<TCVideoInfo>>> getVideoList(@Query("page") int page, @Query("pageSize") int pageSize);
}
