package cn.sanshaoxingqiu.ssbm.module.personal.income.api;

import com.exam.commonbiz.net.BaseResponse;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RankinglistBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RequestBindBankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawRequest;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IncomeApiService {

    @GET("ssxq/withdraw/home")
    Observable<BaseResponse<IncomeBean>> income();

    @GET("rankinglist")
    Observable<BaseResponse<RankinglistBean>> rankinglist();

    @POST("ssxq/bank/card/binding")
    Observable<BaseResponse> bindingBankCard(@Body RequestBindBankCardInfo requestBindBankCardInfo);

    @GET("ssxq/bank/card/list")
    Observable<BaseResponse<List<BankCardInfo>>> getBindedBankList();

    @GET("ssxq/bank/card")
    Observable<BaseResponse<List<BankCardInfo>>> getBankList();

    @POST("ssxq/withdraw/apply/for")
    Observable<BaseResponse> withdraw(@Body WithdrawRequest withdrawRequest);

    @GET("ssxq/withdraw/income/list")
    Observable<BaseResponse<List<IncomeInfo>>> getIncomeList();

    @GET("ssxq/withdraw/list")
    Observable<BaseResponse<List<WithdrawInfo>>> getWithDrawList();
}
