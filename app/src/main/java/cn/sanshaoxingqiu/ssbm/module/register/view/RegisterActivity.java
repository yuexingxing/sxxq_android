package cn.sanshaoxingqiu.ssbm.module.register.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityRegisterBinding;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.model.ILoginCallBack;
import cn.sanshaoxingqiu.ssbm.module.login.viewmodel.LoginViewModel;
import com.exam.commonbiz.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.register.model.IRegisterCallBack;
import cn.sanshaoxingqiu.ssbm.module.register.viewmodel.RegisterViewModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsTypeInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.util.ShoppingCenterUtil;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.GoodsDetailActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.GoodsListActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.GoodsTypeDetailVerticalAdapter;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;

import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;
import com.exam.commonbiz.dialog.CommonTipDialog;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Res;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RegisterActivity extends BaseActivity<RegisterViewModel, ActivityRegisterBinding> implements IRegisterCallBack, ILoginCallBack {

    private GoodsTypeDetailVerticalAdapter goodsTypeDetailVerticalAdapter;
    private LoginViewModel mLoginViewModel;

    public static void start(Context context, String tilte, String artiTagId) {
        if (TextUtils.isEmpty(artiTagId)) return;
        Intent starter = new Intent(context, RegisterActivity.class);
        starter.putExtra(Constants.OPT_DATA, artiTagId);
        starter.putExtra(Constants.OPT_DATA2, tilte);
        context.startActivity(starter);
    }

    @Override
    public void initData() {
        String artiTagId = getIntent().getStringExtra(Constants.OPT_DATA);
        String title = getIntent().getStringExtra(Constants.OPT_DATA2);
        if (!TextUtils.isEmpty(title)) {
            binding.titleBar.setTitle(title);
        }

        mLoginViewModel = new LoginViewModel();
        mLoginViewModel.setCallBack(this);
        mViewModel.setCallBack(this);

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

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
            String invitationCode = binding.edtInviteCode.getText().toString();
//            if (TextUtils.isEmpty(invitationCode)) {
            login();
//            } else {
//                mLoginViewModel.getMemInfoByInvitationCode(invitationCode);
//            }
        });

        if (binding.viewRegisterInfo.getVisibility() == View.VISIBLE) {
            GlideUtil.loadgifImage(R.drawable.icon_register, binding.ivRegister);
        }

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        updateVipCard(userInfo);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(RegisterActivity.this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        goodsTypeDetailVerticalAdapter = new GoodsTypeDetailVerticalAdapter();
        goodsTypeDetailVerticalAdapter.setCommonCallBack((postion, object) -> {
            if (!SSApplication.isLogin()) {
                binding.nestedScrollview.smoothScrollTo(0, 0);
                return;
            }
            GoodsTypeInfo goodsTypeInfo = new GoodsTypeInfo();
            goodsTypeInfo.artitag_id = ShoppingCenterUtil.getRegisterTagId();
            GoodsListActivity.start(context, goodsTypeInfo);
        });
        if (!SSApplication.isLogin()) {
            goodsTypeDetailVerticalAdapter.isShowConver(true);
        } else {
            goodsTypeDetailVerticalAdapter.isShowConver(false);
        }
        goodsTypeDetailVerticalAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!SSApplication.isLogin()) {
                ToastUtil.showShortToast("注册后即可免费领取");
                binding.nestedScrollview.smoothScrollTo(0, 0);
                return;
            }
            GoodsDetailInfo goodsDetailInfo = goodsTypeDetailVerticalAdapter.getData().get(position);
            if (goodsDetailInfo.isPayByPoint()) {
                if (userInfo.available_point == 0) {
                    CommonTipDialog commonTipDialog = new CommonTipDialog();
                    commonTipDialog.init(context)
                            .setTitle("分享金不足")
                            .setContent("分享一位体验用户成功注册三少变美APP，即可获得\"奖励变美区\"一个项目，项目任选，多分享多获得。")
                            .setRightButton("确认")
                            .setOnLeftButtonClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonTipDialog.dismiss();
                                }
                            })
                            .setOnRightButtonClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonTipDialog.dismiss();
                                }
                            })
                            .show();
                    return;
                }
            }
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
        if (userInfo == null) {
            return;
        }
        login();
    }

    @Override
    public void registerSucc(LoginResponse response) {
        if (response == null) {
            return;
        }
        SSApplication.setToken(response.token);
        binding.viewRegisterInfo.setVisibility(View.GONE);
        mViewModel.getUserInfo();
        goodsTypeDetailVerticalAdapter.isShowConver(false);
        goodsTypeDetailVerticalAdapter.notifyDataSetChanged();
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
        if (goodsList.size() % 2 != 0) {
            GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
            goodsDetailInfo.setItemType(GoodsDetailInfo.GOODS_TYPE.WITH_LAST_DATA);
            goodsList.add(goodsDetailInfo);
        }
        goodsTypeDetailVerticalAdapter.setNewData(goodsList);
    }

    @Override
    public void getUserInfoSucc(UserInfo userInfo) {
        updateVipCard(userInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    private void updateVipCard(UserInfo userInfo) {
        String memClassKey = "";
        if (userInfo != null && userInfo.mem_class != null) {
            memClassKey = userInfo.mem_class.mem_class_key;
        }
        if (TextUtils.equals(memClassKey, "1")) {
            binding.ivVipCard.setBackgroundResource(R.drawable.icon_user_vip1);
        } else if (TextUtils.equals(memClassKey, "2")) {
            binding.ivVipCard.setBackgroundResource(R.drawable.icon_user_vip2);
        } else if (TextUtils.equals(memClassKey, "3")) {
            binding.ivVipCard.setBackgroundResource(R.drawable.icon_user_vip3);
        } else {
            binding.ivVipCard.setBackgroundResource(R.drawable.icon_user_normal);
        }
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

    private void login() {
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
    }
}
