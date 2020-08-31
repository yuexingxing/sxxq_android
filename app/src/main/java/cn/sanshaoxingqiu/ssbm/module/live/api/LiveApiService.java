package cn.sanshaoxingqiu.ssbm.module.live.api;


import com.exam.commonbiz.net.BaseResponse;

import cn.sanshaoxingqiu.ssbm.module.invitation.bean.UserReferrals;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LiveApiService {

    @GET("user/referrals")
    Observable<BaseResponse<UserReferrals>> requestUserReferrals();
}
