package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.base.EmptyWebViewActivity;
import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityWithdrawBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IBindBankCardModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.view.dialog.SelectBankCardDialog;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.BindBankCardViewModel;

/**
 * 提现界面
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class WithdrawActivity extends BaseActivity<BindBankCardViewModel, ActivityWithdrawBinding> implements IBindBankCardModel {

    private List<BankCardInfo> mBankCardInfoList;

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
        //添加银行卡
        binding.llAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                    }
                });
                selectBankCardDialog.show(context, mBankCardInfoList);
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

            }
        });
        //全部提现
        binding.tvWithdrawAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getBindedBankList();
    }

    @Override
    public void returnBankList(List<BankCardInfo> bankCardInfoList) {
        if (ContainerUtil.isEmpty(bankCardInfoList)) {
            return;
        }
        mBankCardInfoList = bankCardInfoList;
    }

    @Override
    public void onBindBankCardSuccess() {

    }

    @Override
    public void onBindBankCardFailed() {

    }
}