package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityPayCompleteBinding;
import cn.sanshaoxingqiu.ssbm.module.MainActivity;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import com.exam.commonbiz.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGoodsDetailModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.PaySuccessDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel.GoodsDetailViewModel;
import com.exam.commonbiz.util.BitmapUtil;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;

/**
 * 支付完成
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class PayCompleteActivity extends BaseActivity<GoodsDetailViewModel, ActivityPayCompleteBinding> implements IGoodsDetailModel {

    private String mSartiId;
    private String mSaleBillId;
    private GoodsDetailInfo mGoodsDetailInfo;

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
            if (mGoodsDetailInfo != null) {
                share();
            }
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
        mGoodsDetailInfo = goodsDetailInfo;
        if (goodsDetailInfo.isFree() || goodsDetailInfo.isPayByPoint() || TextUtils.isEmpty(goodsDetailInfo.mem_class_key)) {
            binding.includeStar.layoutBg.setVisibility(View.GONE);
            binding.includeShare.layoutBg.setVisibility(View.VISIBLE);
        } else {
            binding.includeStar.layoutBg.setVisibility(View.VISIBLE);
            binding.includeShare.layoutBg.setVisibility(View.GONE);
            binding.includeStar.llReward.getBackground().setAlpha(9);
            UserInfo userInfo = SSApplication.getInstance().getUserInfo();
            if (userInfo != null) {
                if (!TextUtils.isEmpty(userInfo.mem_class.mem_class_key)) {
                    //比较星级，选择最大的
                    if (!TextUtils.isEmpty(userInfo.mem_class.mem_class_key) && userInfo.mem_class.mem_class_key.compareTo(goodsDetailInfo.mem_class_key) > 0) {
                        goodsDetailInfo.mem_class_key = userInfo.mem_class.mem_class_key;
                    }
                }

                if (goodsDetailInfo.isOneStarMember()) {
                    binding.includeStar.ivStar.setImageResource(R.drawable.image_onestarpaymentissuccessful);
                } else if (goodsDetailInfo.isTwoStarMember()) {
                    binding.includeStar.ivStar.setImageResource(R.drawable.image_twostarpaymentissuccessful);
                } else if (goodsDetailInfo.isThreeStarMember()) {
                    binding.includeStar.ivStar.setImageResource(R.drawable.image_threestarpaymentissuccessful);
                }

                if (userInfo.isMember()) {
                    binding.includeStar.tvPaycompleteTip.setText(String.format("您已经是%s啦！快去分享赚钱吧！", userInfo.getMember()));
                } else {
                    binding.includeStar.tvPaycompleteTip.setText("恭喜您！核销完成后将成为一星粉丝！");
                }
            }

            PaySuccessDialog paySuccessDialog = new PaySuccessDialog();
            paySuccessDialog.show(context, goodsDetailInfo);
        }
    }

    private void share() {

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo("分享到微信"));
//        commonDialogInfoList.add(new CommonDialogInfo("生成海报"));

        new CommonBottomDialog()
                .init(this)
                .setData(commonDialogInfoList)
                .setOnItemClickListener(commonDialogInfo -> {
                    if (commonDialogInfo.position == 0) {

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap bitmap = BitmapUtil.getBitmap(mGoodsDetailInfo.thumbnail_img);
                                Message message = new Message();
                                message.obj = bitmap;
                                mHandler.sendMessage(message);
                            }
                        }).start();
                    } else {
                        new GoodsPosterDialog().show(context, new GoodsDetailInfo());
                    }
                })
                .show();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            new ShareUtils()
                    .init(context)
                    .shareMiniProgram(mGoodsDetailInfo.sarti_name, mGoodsDetailInfo.sarti_desc,
                            (Bitmap) message.obj, mGoodsDetailInfo.getSharePath());
        }
    };
}