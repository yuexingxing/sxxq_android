package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityPayCompleteBinding;
import cn.sanshaoxingqiu.ssbm.module.MainActivity;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.PayCompleteViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.PaySuccessDialog;
import cn.sanshaoxingqiu.ssbm.util.Constants;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.EventBus;

/**
 * 支付完成
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class PayCompleteActivity extends BaseActivity<PayCompleteViewModel, ActivityPayCompleteBinding> {

    private String mSalebillId;
    ;

    public static void start(Context context, String salebillId) {
        Intent starter = new Intent(context, PayCompleteActivity.class);
        starter.putExtra(Constants.OPT_DATA, salebillId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_complete;
    }

    @Override
    public void initData() {

        PayStatusChangedEvent payStatusChangedEvent = new PayStatusChangedEvent();
        payStatusChangedEvent.paySuccess = true;
        EventBus.getDefault().post(payStatusChangedEvent);

        mSalebillId = getIntent().getStringExtra(Constants.OPT_DATA);
        binding.llReward.getBackground().setAlpha(9);
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
        binding.tvToMain.setOnClickListener(v -> MainActivity.start(context));
        binding.tvViewOrder.setOnClickListener(v -> OrderDetailActivity.start(context, mSalebillId));

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        if (userInfo != null) {
            if (userInfo.isMember()) {
                binding.tvPaycompleteTip.setText(String.format("您已经是%s啦！快去分享赚钱吧！", userInfo.getMember()));
            } else {
                binding.tvPaycompleteTip.setText("恭喜您！核销完成后将成为一星会员！");
            }
        }

        binding.guessYouLoveView.getData();
        PaySuccessDialog paySuccessDialog = new PaySuccessDialog();
        paySuccessDialog.show(context);
    }

}