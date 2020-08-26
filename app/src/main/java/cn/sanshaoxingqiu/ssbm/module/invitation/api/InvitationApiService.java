package cn.sanshaoxingqiu.ssbm.module.invitation.api;


import com.exam.commonbiz.net.BaseResponse;
import cn.sanshaoxingqiu.ssbm.module.invitation.bean.UserReferrals;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface InvitationApiService {

    @GET("user/referrals")
    Observable<BaseResponse<UserReferrals>> requestUserReferrals();
}
