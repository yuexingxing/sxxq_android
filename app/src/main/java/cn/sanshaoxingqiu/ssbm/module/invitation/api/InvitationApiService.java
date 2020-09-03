package cn.sanshaoxingqiu.ssbm.module.invitation.api;


import com.exam.commonbiz.net.BaseResponse;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.invitation.bean.UserReferrals;

import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface InvitationApiService {

    @GET("ssxq/user/referrals")
    Observable<BaseResponse<UserReferrals>> requestUserReferrals();

    @GET("user/referrals/point")
    Observable<BaseResponse<List<UserInfo>>> getUserReferralsPoint();
}
