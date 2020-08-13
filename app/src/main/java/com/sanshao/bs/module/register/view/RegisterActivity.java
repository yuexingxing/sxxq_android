package com.sanshao.bs.module.register.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Res;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityRegisterBinding;
import com.sanshao.bs.module.MainActivity;
import com.sanshao.bs.module.login.bean.LoginResponse;
import com.sanshao.bs.module.login.viewmodel.LoginViewModel;
import com.sanshao.bs.module.register.model.IRegisterCallBack;
import com.sanshao.bs.module.register.viewmodel.RegisterViewModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.view.GoodsDetailActivity;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GoodsTypeDetailVerticalAdapter;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.bs.util.ToastUtil;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RegisterActivity extends BaseActivity<RegisterViewModel, ActivityRegisterBinding> implements IRegisterCallBack {

    private GoodsTypeDetailVerticalAdapter goodsTypeDetailVerticalAdapter;

    public static void start(Context context, String artiTagId) {
        Intent starter = new Intent(context, RegisterActivity.class);
        starter.putExtra(Constants.OPT_DATA, artiTagId);
        context.startActivity(starter);
    }

    @Override
    public void initData() {
        String artiTagId = getIntent().getStringExtra(Constants.OPT_DATA);

        mViewModel.setCallBack(this);

        binding.viewRegisterInfo.setVisibility(SSApplication.isLogin() ? View.GONE : View.VISIBLE);
        binding.tvGetCode.setOnClickListener(v -> {
            String mPhone = binding.edtPhone.getText().toString();
            if (!CommandTools.isMobileNum(mPhone)) {
                ToastUtil.showShortToast("请输入正确的手机号");
                return;
            }
            LoadDialogMgr.getInstance().show(context);
            mViewModel.getSMSCode(mPhone, LoginViewModel.LoginType.APP_LOGIN);
        });
        binding.ivRegister.setOnClickListener(v -> {
            String mPhone = binding.edtPhone.getText().toString();
            String code = binding.edtCode.getText().toString();
            String referrerMemId = binding.edtInviteCode.getText().toString();
            if (!CommandTools.isMobileNum(mPhone)) {
                ToastUtil.showShortToast("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(code)) {
                ToastUtil.showShortToast("验证码不能为空");
                return;
            }
            LoadDialogMgr.getInstance().show(context, "注册中...");
            mViewModel.reister(mPhone, code, referrerMemId);
        });
        if (binding.viewRegisterInfo.getVisibility() == View.VISIBLE) {
            GlideUtil.loadgifImage(R.drawable.icon_register, binding.ivRegister);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RegisterActivity.this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        goodsTypeDetailVerticalAdapter = new GoodsTypeDetailVerticalAdapter();
        goodsTypeDetailVerticalAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!SSApplication.isLogin()) {
                ToastUtil.showShortToast("注册后即可免费领取");
                return;
            }
            GoodsDetailInfo goodsDetailInfo = goodsTypeDetailVerticalAdapter.getData().get(position);
            GoodsDetailActivity.start(view.getContext(), goodsDetailInfo.sarti_id);
        });
        binding.goodsRecyclerView.setNestedScrollingEnabled(false);
        binding.goodsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.goodsRecyclerView.setAdapter(goodsTypeDetailVerticalAdapter);

        mViewModel.getGoodsList(artiTagId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void onGetCode() {
        timer.start();
        binding.tvGetCode.setEnabled(false);
    }

    @Override
    public void registerSucc(LoginResponse response) {
        if (response == null) {
            return;
        }
        SSApplication.setToken(response.token);
        MainActivity.start(context);
        this.finish();
    }

    @Override
    public void registerFail() {

    }

    @Override
    public void showGoods(List<GoodsDetailInfo> goodsList) {
        if (goodsList == null || goodsList.size() == 0) {
            binding.ivVipCard.setVisibility(View.GONE);
            binding.viewActivityLine.setVisibility(View.GONE);
            binding.viewActivityTitle.setVisibility(View.GONE);
            binding.goodsRecyclerView.setVisibility(View.GONE);
            binding.viewActivityDescription.setVisibility(View.GONE);
            return;
        }
        goodsTypeDetailVerticalAdapter.setNewData(goodsList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
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
}
