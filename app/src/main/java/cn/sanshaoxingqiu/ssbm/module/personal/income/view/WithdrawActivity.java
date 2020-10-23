package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.EmptyWebViewActivity;
import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.Calendar;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityWithdrawBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawRequest;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IBindBankCardModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeViewCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.income.view.dialog.SelectBankCardDialog;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.BindBankCardViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.IncomeViewModel;
import cn.sanshaoxingqiu.ssbm.util.MathUtil;

/**
 * 提现界面
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class WithdrawActivity extends BaseActivity<BindBankCardViewModel, ActivityWithdrawBinding> implements IBindBankCardModel, IncomeViewCallBack, View.OnClickListener {

    private List<BankCardInfo> mBankCardInfoList;
    private IncomeViewModel mIncomeViewModel;
    private String mBankCardId;
    private IncomeBean mIncomeBean;

    public static void start(Context context) {
        Intent starter = new Intent(context, WithdrawActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initData() {

        mIncomeViewModel = new IncomeViewModel();
        mIncomeViewModel.setCallBack(this);

        mViewModel.setBindBankCardModel(this);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }

            @Override
            public void onTitleClick(View view) {

            }

            @Override
            public void onRightClick(View view) {

            }
        });
        binding.llAddCard.setOnClickListener(this);
        binding.llBankCard.setOnClickListener(this);

        binding.edtWithdrawFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    binding.tvServiceFee.setText("¥0");
                    return;
                }

                int withdraw = Integer.parseInt(charSequence.toString());
                double serviceFee = withdraw * 0.0085;
                if (serviceFee > 0 && serviceFee < 0.85) {
                    serviceFee = 0.85;
                }
                binding.tvServiceFee.setText("¥" + MathUtil.getNumExclude0(serviceFee));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //提现协议
        binding.tvWithdrawPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmptyWebViewActivity.start(context, "", Constants.withdrawalrulesUrl);
            }
        });
        //开始提现
        binding.tvWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWithdraw();
            }
        });
        //全部提现
        binding.tvWithdrawAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIncomeBean == null) {
                    return;
                }
                binding.edtWithdrawFee.setText((int) mIncomeBean.used_price + "");
            }
        });

        mIncomeViewModel.requestIncomeInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getBindedBankList();
    }

    private void startWithdraw() {
        if (mIncomeBean == null) {
            return;
        }

        if (!binding.checkboxPolicy.isChecked()) {
            ToastUtil.showShortToast("请先同意提现协议");
            return;
        }

        if (TextUtils.isEmpty(mBankCardId)) {
            ToastUtil.showShortToast("请选择银行卡");
            return;
        }

        String withdrawFeeStr = binding.edtWithdrawFee.getText().toString();
        if (TextUtils.isEmpty(withdrawFeeStr)) {
            ToastUtil.showShortToast("请输入提现金额");
            return;
        }

        int withDraw = Integer.parseInt(withdrawFeeStr);
        if (withDraw == 0) {
            ToastUtil.showShortToast("提现金额不能为0");
            return;
        }
        if (withDraw > mIncomeBean.used_price) {
            ToastUtil.showShortToast("提现金额不能超过可提现金额");
            return;
        }

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.withdraw_amount = withDraw;
        withdrawRequest.withdraw_bank_card_id = mBankCardId;
        mIncomeViewModel.withdraw(withdrawRequest);
    }

    private void setmBankCardInfo(BankCardInfo bankCardInfo) {

        mBankCardId = bankCardInfo.mem_bank_card_id;
        binding.tvBankName.setText(bankCardInfo.bank_name);
        binding.tvBankCardNo.setText(bankCardInfo.bank_card_number);
        GlideUtil.loadImage(bankCardInfo.bank_card_icon, binding.ivBanIcon);
    }

    @Override
    public void returnBankList(List<BankCardInfo> bankCardInfoList) {
        if (ContainerUtil.isEmpty(bankCardInfoList)) {
            binding.llAddCard.setVisibility(View.VISIBLE);
            binding.llBankCard.setVisibility(View.GONE);
            return;
        }
        mBankCardInfoList = bankCardInfoList;
        BankCardInfo bankCardInfo = mBankCardInfoList.get(0);
        bankCardInfo.checked = true;
        setmBankCardInfo(bankCardInfo);

        binding.llAddCard.setVisibility(View.GONE);
        binding.llBankCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindBankCardSuccess() {

    }

    @Override
    public void onBindBankCardFailed() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_add_card || view.getId() == R.id.ll_bank_card) {
            if (ContainerUtil.isEmpty(mBankCardInfoList)) {
                return;
            }
            SelectBankCardDialog selectBankCardDialog = new SelectBankCardDialog();
            selectBankCardDialog.setItemClickListener(new SelectBankCardDialog.ItemClickListener() {
                @Override
                public void addNewBankCard() {
                    BindBankCardActivity.start(context);
                }

                @Override
                public void onItemClick(BankCardInfo bankCardInfo) {
                    setmBankCardInfo(bankCardInfo);
                }
            });
            selectBankCardDialog.show(context, mBankCardInfoList);
        }
    }

    @Override
    public void requestIncomeInfoSucc(IncomeBean bean) {
        if (bean == null) {
            return;
        }

        mIncomeBean = bean;
        binding.tvFee1.setText(MathUtil.getNumExclude0(bean.used_price) + "");
        binding.tvFee2.setText("待入账：" + MathUtil.getNumExclude0(bean.underway) + "元");
    }

    @Override
    public void withdraw() {
        finish();
    }

    @Override
    public void returnIncomeRecordList(List<IncomeInfo> incomeInfoList) {

    }

    @Override
    public void returnWithdrawRecordList(List<WithdrawInfo> withdrawInfoList) {

    }

    @Override
    public LoadingDialog createLoadingDialog() {
        return null;
    }

    @Override
    public LoadingDialog createLoadingDialog(String text) {
        return null;
    }

    @Override
    public boolean visibility() {
        return false;
    }

    @Override
    public boolean viewFinished() {
        return false;
    }
}