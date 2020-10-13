package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.ToastUtil;
import com.sanshao.commonui.pickerview.builder.OptionsPickerBuilder;
import com.sanshao.commonui.pickerview.contrarywind.view.WheelView;
import com.sanshao.commonui.pickerview.listener.OnOptionsSelectListener;
import com.sanshao.commonui.pickerview.view.OptionsPickerView;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityBindBankCardBinding;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.model.ILoginCallBack;
import cn.sanshaoxingqiu.ssbm.module.login.viewmodel.LoginViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RequestBindBankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IBindBankCardModel;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.BindBankCardViewModel;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;

/**
 * 绑定银行卡
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class BindBankCardActivity extends BaseActivity<BindBankCardViewModel, ActivityBindBankCardBinding> implements ILoginCallBack, IBindBankCardModel {

    private String mPhone;
    private LoginViewModel mLoginViewModel;
    private String mBankId;
    private List<BankCardInfo> mBankCardInfoList;

    public static void start(Context context) {
        Intent starter = new Intent(context, BindBankCardActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    public void initData() {

        mLoginViewModel = new LoginViewModel();
        mLoginViewModel.setCallBack(this);
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

        binding.ivSelBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBankPicker();
            }
        });

        binding.tvGetCode.setOnClickListener(v -> {
            mPhone = binding.edtPhone.getText().toString();
            if (!CommandTools.isMobileNum(mPhone)) {
                ToastUtil.showShortToast("请输入正确的手机号");
                return;
            }
            LoadDialogMgr.getInstance().show(context);
            mLoginViewModel.getSMSCode(mPhone, LoginViewModel.LoginType.APP_LOGIN);
        });

        binding.tvStartBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBind();
            }
        });
        mViewModel.getBankList();
    }

    private void startBind() {
        String name = binding.edtName.getText().toString();
        String idCard = binding.edtId.getText().toString();
        String bankCardNo = binding.edtBankCardNo.getText().toString();
        String bankName = binding.edtBankName.getText().toString();
        String openBank = binding.edtOpenBank.getText().toString();
        String verifyCode = binding.edtCode.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShortToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            ToastUtil.showShortToast("请输入身份证号");
            return;
        }
        if (TextUtils.isEmpty(bankCardNo)) {
            ToastUtil.showShortToast("请输入银行卡号");
            return;
        }
        if (TextUtils.isEmpty(bankName)) {
            ToastUtil.showShortToast("请输入所属银行");
            return;
        }
        if (TextUtils.isEmpty(openBank)) {
            ToastUtil.showShortToast("请输入开户行名称");
            return;
        }
        if (TextUtils.isEmpty(mPhone)) {
            ToastUtil.showShortToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            ToastUtil.showShortToast("请输入验证码");
            return;
        }

        RequestBindBankCardInfo bankCardInfo = new RequestBindBankCardInfo();
        bankCardInfo.account_holder_name = name;
        bankCardInfo.account_holder_id_number = idCard;
        bankCardInfo.bank_card_number = bankCardNo;
        bankCardInfo.bank_id = mBankId;
        bankCardInfo.account_holder_phone = mPhone;
        bankCardInfo.opening_bank_name = openBank;
        bankCardInfo.code = verifyCode;

        mViewModel.bindingBankCard(bankCardInfo);
    }

    //Dialog 模式下，在底部弹出
    private void initBankPicker() {
        if (ContainerUtil.isEmpty(mBankCardInfoList)) {
            return;
        }
        final List<String> dataList = new ArrayList<>();
        for (int i = 0; i < mBankCardInfoList.size(); i++) {
            dataList.add(mBankCardInfoList.get(i).bank_name);
        }
        OptionsPickerView mOptionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                BankCardInfo bankCardInfo = mBankCardInfoList.get(options1);
                mBankId = bankCardInfo.bank_card_id;
                binding.edtBankName.setText(bankCardInfo.bank_name);
            }
        })
                .setTitleText("所属银行")
                .setSelectOptions(0)
                .setItemVisibleCount(5)
                .setDividerType(WheelView.DividerType.WRAP)
                .build();
        mOptionsPickerView.setPicker(dataList);
        mOptionsPickerView.setDialog();
        mOptionsPickerView.show();
    }

    /**
     * 取消计时
     */
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * 计时器
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            binding.tvGetCode.setText((millisUntilFinished / 1000) + "s");
            binding.tvGetCode.setTextColor(Res.getColor(SSApplication.app, R.color.color_b6a578));
        }

        @Override
        public void onFinish() {
            binding.tvGetCode.setEnabled(true);
            binding.tvGetCode.setText("重新发送");
            binding.tvGetCode.setTextColor(Res.getColor(SSApplication.app, R.color.color_333333));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @Override
    public void onGetCode() {
        timer.start();
        binding.tvGetCode.setEnabled(false);
    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {

    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onModifyPhone(String phone) {

    }

    @Override
    public void onMemInfoByInvitationCode(UserInfo userInfo) {

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
        finish();
    }

    @Override
    public void onBindBankCardFailed() {

    }
}