package com.sanshao.bs.module.personal.income.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.personal.income.bean.IncomeBean;
import com.sanshao.bs.module.personal.income.bean.RankinglistBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IncomeApiService {


    @GET("income")
    Observable<BaseResponse<IncomeBean>> income();

    @GET("rankinglist")
    Observable<BaseResponse<RankinglistBean>> rankinglist();
}
