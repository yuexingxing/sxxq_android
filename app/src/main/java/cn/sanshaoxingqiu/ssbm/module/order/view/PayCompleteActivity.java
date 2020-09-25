package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.Constants;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.EventBus;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityPayCompleteBinding;
import cn.sanshaoxingqiu.ssbm.module.MainActivity;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGoodsDetailModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.ExerciseActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.PaySuccessDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel.GoodsDetailViewModel;

/**
 * 支付完成
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class PayCompleteActivity extends BaseActivity<GoodsDetailViewModel, ActivityPayCompleteBinding> implements IGoodsDetailModel {

    private String mSartiId;
    private String mSaleBillId;

    public static void start(Context context, String sartiId, String saleBillId) {
        Intent starter = new Intent(context, PayCompleteActivity.class);
        starter.putExtra(Constants.OPT_DATA, sartiId);
        starter.putExtra(Constants.OPT_DATA2, saleBillId);
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

        mViewModel.setCallBack(this);
        mSartiId = getIntent().getStringExtra(Constants.OPT_DATA);
        mSaleBillId = getIntent().getStringExtra(Constants.OPT_DATA2);
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
        binding.includeShare.tvShare.setOnClickListener(v -> {
            ExerciseActivity.start(context, "一起拉用户", Constants.userUrl);
        });
        binding.includeStar.tvMember.setOnClickListener(v -> {
            ExerciseActivity.start(context, "会员权益", Constants.memberBenefitUrl);
        });
        binding.tvToMain.setOnClickListener(v -> MainActivity.start(context));
        binding.tvViewOrder.setOnClickListener(v -> {
            OrderDetailActivity.start(context, OrderInfo.State.ToBeUse, mSaleBillId);
            finish();
        });
        mViewModel.getGoodsDetail(context, mSartiId);
        binding.guessYouLoveView.getData();
    }

    @Override
    public void returnGoodsDetail(GoodsDetailInfo goodsDetailInfo) {
        if (goodsDetailInfo == null) {
            return;
        }
        if (goodsDetailInfo.isFree() || goodsDetailInfo.isPayByPoint()) {
            binding.includeStar.layoutBg.setVisibility(View.GONE);
            binding.includeShare.layoutBg.setVisibility(View.VISIBLE);
        } else {
            binding.includeStar.layoutBg.setVisibility(View.VISIBLE);
            binding.includeShare.layoutBg.setVisibility(View.GONE);
            UserInfo userInfo = SSApplication.getInstance().getUserInfo();
            if (userInfo != null) {
                if (!TextUtils.isEmpty(userInfo.mem_class.mem_class_key)) {
                    //比较星级，选择最大的
                    if (!TextUtils.isEmpty(userInfo.mem_class.mem_class_key) && userInfo.mem_class.mem_class_key.compareTo(goodsDetailInfo.mem_class_key) > 0) {
                        goodsDetailInfo.mem_class_key = userInfo.mem_class.mem_class_key;
                    }
                }

                String star = "一星粉丝";
                if (goodsDetailInfo.isOneStarMember()) {
                    star = "一星粉丝";
                    binding.includeStar.ivStar.setImageResource(R.drawable.image_onestarpaymentissuccessful);
                } else if (goodsDetailInfo.isTwoStarMember()) {
                    star = "二星粉丝";
                    binding.includeStar.ivStar.setImageResource(R.drawable.image_twostarpaymentissuccessful);
                } else if (goodsDetailInfo.isThreeStarMember()) {
                    star = "三星粉丝";
                    binding.includeStar.ivStar.setImageResource(R.drawable.image_threestarpaymentissuccessful);
                }

                if (userInfo.isMember()) {
                    binding.includeStar.tvPaycompleteTip.setText(String.format("您已经是%s啦！快去分享赚钱吧！", star));
                } else {
                    binding.includeStar.tvPaycompleteTip.setText("恭喜您已成为【三少变美】一星粉丝");
                }
            }

            PaySuccessDialog paySuccessDialog = new PaySuccessDialog();
            paySuccessDialog.show(context, goodsDetailInfo);
        }
    }
}