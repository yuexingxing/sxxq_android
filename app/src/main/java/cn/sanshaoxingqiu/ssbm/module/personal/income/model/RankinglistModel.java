package cn.sanshaoxingqiu.ssbm.module.personal.income.model;

import com.exam.commonbiz.net.BaseObserver;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.ExceptionHandle;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.XApi;
import cn.sanshaoxingqiu.ssbm.module.personal.income.api.IncomeApiService;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RankinglistBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RankinglistModel {

    public static void requestRankinglist(OnLoadListener<RankinglistBean> onLoadListener){
        XApi.get(IncomeApiService.class)
                .rankinglist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RankinglistBean>() {

                    @Override
                    public void onStart() {
                        onLoadListener.onLoadStart();
                    }

                    @Override
                    public void onSuccess(BaseResponse<RankinglistBean> response) {
                        onLoadListener.onLoadSucessed(response);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        onLoadListener.onLoadFailed(responeThrowable.message);
                    }

                    @Override
                    public void onFinish() {
                        onLoadListener.onLoadCompleted();
                    }

                });
    }
}
