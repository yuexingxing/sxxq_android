package cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.SimpleLoadCallBack;
import com.exam.commonbiz.util.ToastUtil;

import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IBindBankCardModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeViewCallBack;

public class IncomeViewModel extends BaseViewModel {

    private IncomeViewCallBack callBack;
    private IBindBankCardModel mIBindBankCardModel;

    public IncomeViewModel() {
    }

    public void setCallBack(IncomeViewCallBack callBack) {
        this.callBack = callBack;
    }

    public void setBindBankCardModel(IBindBankCardModel iBindBankCardModel) {
        mIBindBankCardModel = iBindBankCardModel;
    }

    public void requestIncomeInfo() {
        IncomeModel.requestIncomeInfo(new SimpleLoadCallBack<IncomeBean>(callBack) {

            @Override
            public void onLoadSucessed(BaseResponse<IncomeBean> bean) {
                if (callBack != null) {
                    callBack.requestIncomeInfoSucc(bean.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (callBack != null) {
                    callBack.requestIncomeInfoFail(errMsg);
                }
            }
        });
    }
}
