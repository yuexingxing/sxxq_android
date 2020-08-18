package com.sanshao.bs.module.invitation.api;


import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.invitation.bean.UserReferrals;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface InvitationApiService {

    @GET("user/referrals")
    Observable<BaseResponse<UserReferrals>> requestUserReferrals();
}
