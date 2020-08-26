package cn.sanshaoxingqiu.ssbm.module.personal.income.api;

import com.exam.commonbiz.net.BaseResponse;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RankinglistBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IncomeApiService {


    @GET("income")
    Observable<BaseResponse<IncomeBean>> income();

    @GET("rankinglist")
    Observable<BaseResponse<RankinglistBean>> rankinglist();
}
