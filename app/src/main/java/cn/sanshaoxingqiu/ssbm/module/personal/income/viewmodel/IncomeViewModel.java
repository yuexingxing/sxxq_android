package cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.SimpleLoadCallBack;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawRequest;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IBindBankCardModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeViewCallBack;

public class IncomeViewModel extends BaseViewModel {

    private IncomeViewCallBack mIncomeViewCallBack;

    public IncomeViewModel() {
    }

    public void setCallBack(IncomeViewCallBack callBack) {
        this.mIncomeViewCallBack = callBack;
    }

    public void requestIncomeInfo() {
        IncomeModel.requestIncomeInfo(new OnLoadListener<IncomeBean>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<IncomeBean> t) {
                if (mIncomeViewCallBack != null) {
                    mIncomeViewCallBack.requestIncomeInfoSucc(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void withdraw(WithdrawRequest withdrawRequest) {
        IncomeModel.withdraw(withdrawRequest, new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                ToastUtil.showShortToast(t.getMsg());
                if (t.isOk() && mIncomeViewCallBack != null) {
                    mIncomeViewCallBack.withdraw();
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void getIncomeList() {
        IncomeModel.getIncomeList(new OnLoadListener<List<IncomeInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<List<IncomeInfo>> t) {
                if (mIncomeViewCallBack != null) {
                    mIncomeViewCallBack.returnIncomeRecordList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
                if (mIncomeViewCallBack != null) {
                    mIncomeViewCallBack.returnIncomeRecordList(null);
                }
            }
        });
    }

    public void getWithDrawList() {
        IncomeModel.getWithDrawList(new OnLoadListener<List<WithdrawInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<List<WithdrawInfo>> t) {
                if (mIncomeViewCallBack != null) {
                    mIncomeViewCallBack.returnWithdrawRecordList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }
}
