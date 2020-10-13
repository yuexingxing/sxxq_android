package cn.sanshaoxingqiu.ssbm.module.personal.income.model;

import com.exam.commonbiz.net.BaseObserver;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.ExceptionHandle;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.XApi;

import cn.sanshaoxingqiu.ssbm.module.personal.income.api.IncomeApiService;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;

import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RequestBindBankCardInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IncomeModel {

    public static void requestIncomeInfo(OnLoadListener<IncomeBean> onLoadListener) {
        XApi.get(IncomeApiService.class)
                .income()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<IncomeBean>() {

                    @Override
                    public void onStart() {
                        onLoadListener.onLoadStart();
                    }

                    @Override
                    public void onSuccess(BaseResponse<IncomeBean> response) {
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

    public static void getBankList(OnLoadListener onLoadListener) {
        XApi.get(IncomeApiService.class)
                .getBankList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {

                    @Override
                    public void onStart() {
                        onLoadListener.onLoadStart();
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
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

    public static void bindingBankCard(RequestBindBankCardInfo requestBindBankCardInfo, OnLoadListener onLoadListener) {
        XApi.get(IncomeApiService.class)
                .bindingBankCard(requestBindBankCardInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {

                    @Override
                    public void onStart() {
                        onLoadListener.onLoadStart();
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
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
