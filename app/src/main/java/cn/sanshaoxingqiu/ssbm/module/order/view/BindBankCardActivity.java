package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.bean.UserInfo;
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
import cn.sanshaoxingqiu.ssbm.util.CommandTools;

/**
 * 绑定银行卡
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class BindBankCardActivity extends BaseActivity<BaseViewModel, ActivityBindBankCardBinding> implements ILoginCallBack {

    private String mPhone;
    private LoginViewModel mLoginViewModel;

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
                initOptionPicker();
            }
        });

        binding.tvGetCode.setOnClickListener(v -> {
            mPhone = binding.edtPhone.getText().toString();
            if (!CommandTools.isMobileNum(mPhone)) {
                ToastUtil.showShortToast("请输入正确的手机号");
                return;
            }
            LoadDialogMgr.getInstance().show(context);
            mLoginViewModel.getSMSCode(mPhone, LoginViewModel.LoginType.BIND_PHONE);
        });
    }

    //Dialog 模式下，在底部弹出
    private void initOptionPicker() {
        final List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("中国建设银行");
        }
        OptionsPickerView mOptionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Toast.makeText(context, options1 + "-" + dataList.get(options1), Toast.LENGTH_LONG).show();
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
}