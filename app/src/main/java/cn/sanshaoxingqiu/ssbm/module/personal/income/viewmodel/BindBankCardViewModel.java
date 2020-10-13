package cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.util.ToastUtil;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RequestBindBankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IBindBankCardModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeModel;

public class BindBankCardViewModel extends BaseViewModel {

    private IBindBankCardModel mIBindBankCardModel;

    public BindBankCardViewModel() {
    }

    public void setBindBankCardModel(IBindBankCardModel iBindBankCardModel) {
        mIBindBankCardModel = iBindBankCardModel;
    }

    public void getBankList() {
        IncomeModel.getBankList(new OnLoadListener<List<BankCardInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<List<BankCardInfo>> t) {
                ToastUtil.showShortToast(t.getMsg());
                if (mIBindBankCardModel != null) {
                    mIBindBankCardModel.returnBankList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void bindingBankCard(RequestBindBankCardInfo requestBindBankCardInfo) {
        IncomeModel.bindingBankCard(requestBindBankCardInfo, new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                ToastUtil.showShortToast(t.getMsg());
                if (mIBindBankCardModel != null) {
                    if (t.isOk()) {
                        mIBindBankCardModel.onBindBankCardSuccess();
                    } else {
                        mIBindBankCardModel.onBindBankCardFailed();
                    }
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }
}
