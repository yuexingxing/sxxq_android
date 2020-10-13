package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.base.EmptyWebViewActivity;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.BitmapUtil;
import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.ScreenUtil;
import com.exam.commonbiz.util.ToastUtil;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityGoodsDetailBinding;
import cn.sanshaoxingqiu.ssbm.module.home.model.BannerInfo;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderBenefitResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderPayInfoResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderDetailModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.IPayModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.OnPayListener;
import cn.sanshaoxingqiu.ssbm.module.order.util.PayUtils;
import cn.sanshaoxingqiu.ssbm.module.order.view.ConfirmPayActivity;
import cn.sanshaoxingqiu.ssbm.module.order.view.PayCompleteActivity;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderDetailViewModel;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderViewModel;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.PayViewModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.VideoInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGoodsDetailModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.SetMealAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.GoodsInroductionDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.RecommendRewardDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel.GoodsDetailViewModel;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.MathUtil;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;

/**
 * 商品详情
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailViewModel, ActivityGoodsDetailBinding> implements IGoodsDetailModel, IOrderModel,
        IOrderDetailModel, IPayModel {
    private final String TAG = GoodsDetailActivity.class.getSimpleName();
    private String mSartiId;
    private SetMealAdapter mSetMealAdapter;
    private GoodsDetailInfo mGoodsDetailInfo;
    private UserInfo mUserInfo;
    private OrderViewModel mOrderViewModel;
    private OrderDetailViewModel mOrderDetailViewModel;
    private PayViewModel mPayViewModel;
    private String mPayType;

    public static void start(Context context, String sartiId) {
        Intent starter = new Intent(context, GoodsDetailActivity.class);
        starter.putExtra(Constants.OPT_DATA, sartiId);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {

        mUserInfo = SSApplication.getInstance().getUserInfo();
        mSartiId = getIntent().getStringExtra(Constants.OPT_DATA);
        mViewModel.setCallBack(this);
        mOrderViewModel = new OrderViewModel();
        mOrderDetailViewModel = new OrderDetailViewModel();
        mPayViewModel = new PayViewModel();

        mOrderViewModel.setCallBack(this);
        mOrderDetailViewModel.setCallBack(this);
        mPayViewModel.setCallBack(this);
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

        binding.emptyLayout.setOnButtonClick(view -> mViewModel.getGoodsDetail(context, mSartiId));
        binding.nestedScrollview.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > ScreenUtil.dp2px(context, 200)) {
                binding.llTab.setVisibility(View.VISIBLE);
            } else {
                binding.llTab.setVisibility(View.GONE);
            }

            int[] location = new int[2];
            binding.llGoodsDetail.getLocationInWindow(location);
            if (location[1] < 1600) {
                initTabStatus(1);
            } else if (location[1] < 2500) {
                initTabStatus(0);
            } else {
                binding.llTab.setVisibility(View.GONE);
            }
        });

        binding.llIntroduction.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.tvGoodsIntro.getText().toString())) {
                return;
            }
            new GoodsInroductionDialog().show(context, "商品说明", binding.tvGoodsIntro.getText().toString());
        });

        binding.includeBottom.btnBuy.setOnClickListener(v -> {
            GoodsPresenter.startBuy(context, mGoodsDetailInfo);
        });
        binding.llTabGoods.setOnClickListener(v -> {
            initTabStatus(0);
            binding.nestedScrollview.smoothScrollTo(0, 0);
        });
        binding.llTabGoodsDetail.setOnClickListener(v -> {
            initTabStatus(1);
            binding.nestedScrollview.smoothScrollTo(0, binding.llGoodsDetail.getTop() - ScreenUtil.dp2px(context, 60));
        });
        binding.includeBottom.llConsult.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
                return;
            }
            CommandTools.startServiceChat();
        });
        binding.homeBannerLayout.setDotGravityBottomRight();

        //在第一次调用RichText之前先调用RichText.initCacheDir()方法设置缓存目录，不设置会报错
        RichText.initCacheDir(this);
        mSetMealAdapter = new SetMealAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewSetmeal.setLayoutManager(linearLayoutManager);
        binding.recyclerViewSetmeal.setAdapter(mSetMealAdapter);
        binding.recyclerViewSetmeal.setNestedScrollingEnabled(false);
        binding.recyclerViewSetmeal.setFocusable(false);
        ((DefaultItemAnimator) binding.recyclerViewSetmeal.getItemAnimator()).setSupportsChangeAnimations(false);
        mSetMealAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsDetailActivity.start(context, mSetMealAdapter.getData().get(position).sarti_id);
        });

        binding.includeBottom.llShare.setOnClickListener(v -> {
            share();
        });
        binding.ivRecommendReward.setOnClickListener(v -> {
                    if (mGoodsDetailInfo == null) {
                        return;
                    }
                    new RecommendRewardDialog().show(context, mGoodsDetailInfo);
                }
        );
        initTabStatus(1);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.homeBannerLayout.getLayoutParams();
        int videoHeight = ScreenUtil.getScreenWidth(SSApplication.app) - ScreenUtil.dp2px(SSApplication.app, 24);
        layoutParams.height = videoHeight;
        binding.homeBannerLayout.setLayoutParams(layoutParams);
        binding.ivCallPhone.setOnClickListener(view -> CommandTools.showCall(context));
        binding.llHospital.setOnClickListener(view -> EmptyWebViewActivity.start(context, "", Constants.hospitalUrl));
        mViewModel.getGoodsDetail(context, mSartiId);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mPayType)) {
            mPayViewModel.fVipPay(PayViewModel.CHECK_ORDER_STATUS, mPayType);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    /**
     * @param index 0-商品 1-详情
     */
    private void initTabStatus(int index) {
        binding.viewGoodsLine.setVisibility(View.INVISIBLE);
        binding.viewGoodsDetailLine.setVisibility(View.INVISIBLE);
        binding.tvTabGoods.setTextColor(Res.getColor(context, R.color.color_333333));
        binding.tvTabGoodsDetail.setTextColor(Res.getColor(context, R.color.color_333333));
        if (index == 0) {
            binding.viewGoodsLine.setVisibility(View.VISIBLE);
            binding.tvTabGoods.setTextColor(Res.getColor(context, R.color.color_b6a578));
        } else {
            binding.viewGoodsDetailLine.setVisibility(View.VISIBLE);
            binding.tvTabGoodsDetail.setTextColor(Res.getColor(context, R.color.color_b6a578));
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayStatusChangedEvent(PayStatusChangedEvent payStatusChangedEvent) {
        if (payStatusChangedEvent == null) {
            return;
        }
        if (payStatusChangedEvent.paySuccess) {
            finish();
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

    @Override
    public void returnGoodsDetail(GoodsDetailInfo goodsDetailInfo) {
        if (goodsDetailInfo == null) {
            binding.emptyLayout.showError();
            binding.llTab.setVisibility(View.GONE);
            binding.rlBottomView.setVisibility(View.GONE);
            return;
        }
        mGoodsDetailInfo = goodsDetailInfo;
        binding.rlBottomView.setVisibility(View.VISIBLE);
        binding.emptyLayout.showSuccess();

        binding.tvGoodsName.setText(goodsDetailInfo.sarti_name);
        binding.tvOldPrice.setText("¥" + MathUtil.getNumExclude0(goodsDetailInfo.sarti_mkprice));
        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        binding.tvSellNum.setText("已售" + (TextUtils.isEmpty(goodsDetailInfo.sell_num) ? "0" : goodsDetailInfo.sell_num));
        binding.tvGoodsIntro.setText(goodsDetailInfo.sarti_intro);

        binding.tvPrice.setText(goodsDetailInfo.getPriceText());
        if (goodsDetailInfo.isPayByMoney()) {
            binding.includeBottom.btnBuy.setText("立即购买");
            binding.ivRecommendReward.setBackgroundResource(R.drawable.tuijianyou);
        } else {
            if (goodsDetailInfo.isFree()) {
                binding.includeBottom.btnBuy.setText("免费领取");
                binding.ivRecommendReward.setBackgroundResource(R.drawable.image_new_user_free_get);
            } else if (goodsDetailInfo.isPayByPoint()) {
                binding.tvOldPrice.setVisibility(View.GONE);
                binding.includeBottom.btnBuy.setText("分享金购买");
                binding.ivRecommendReward.setBackgroundResource(R.drawable.share_icon);
            } else if (goodsDetailInfo.isPayByDisposit()) {
                binding.tvDepositFee.setVisibility(View.VISIBLE);
                binding.tvDepositFee.setText(goodsDetailInfo.getPriceText());
                binding.tvPrice.setText("零售价：¥" + MathUtil.getNumExclude0(goodsDetailInfo.sarti_saleprice));
                binding.tvOldPrice.setText("原价：" + binding.tvOldPrice.getText().toString());

                binding.tvPrice.setTextSize(12f);
                binding.tvOldPrice.setTextSize(12f);
                binding.tvPrice.setTextColor(Res.getColor(context, R.color.color_999999));
            }
            if (!SSApplication.isLogin()) {
                binding.includeBottom.btnBuy.setText("免费领取");
            }
        }

        RichText.from(goodsDetailInfo.sarti_desc).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(binding.tvGoodsDetail);

        binding.llSetMeal.setVisibility(View.GONE);
        binding.tryMatching.setVisibility(View.GONE);
        if (!ContainerUtil.isEmpty(goodsDetailInfo.product_list)) {
            //0=非套餐，1=套餐
//            if (goodsDetailInfo.is_package == 1) {
//                binding.tryMatching.setVisibility(View.GONE);
//                binding.llSetMeal.setVisibility(View.GONE);
//                mSetMealAdapter.setNewData(goodsDetailInfo.product_list);
//            } else {
//                binding.llSetMeal.setVisibility(View.GONE);
//                binding.tryMatching.setVisibility(View.VISIBLE);
//                binding.tryMatching.setData(goodsDetailInfo.product_list);
//                binding.tryMatching.initViewPager(getSupportFragmentManager());
//            }
        } else {
            binding.llSetMeal.setVisibility(View.GONE);
            binding.tryMatching.setVisibility(View.GONE);
        }

        if (!ContainerUtil.isEmpty(goodsDetailInfo.sarti_img)) {
            List<BannerInfo> bannerInfoList = new ArrayList<>();
            for (int i = 0; i < goodsDetailInfo.sarti_img.size(); i++) {
                VideoInfo videoInfo = goodsDetailInfo.sarti_img.get(i);
                BannerInfo bannerInfo = new BannerInfo();
                if (!TextUtils.isEmpty(videoInfo.video)) {
                    bannerInfo.videoUrl = videoInfo.video;
                    bannerInfo.videoPic = videoInfo.img;
                } else {
                    bannerInfo.artitag_url = videoInfo.img;
                }
                bannerInfoList.add(bannerInfo);
            }
            binding.homeBannerLayout.setRadius(0);
            binding.homeBannerLayout.setLeftMargin(0);
            binding.homeBannerLayout.setData(bannerInfoList);
        }
        binding.nestedScrollview.scrollTo(0, 0);
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            new ShareUtils()
                    .init(context)
                    .shareMiniProgram(mGoodsDetailInfo.sarti_name, mGoodsDetailInfo.sarti_desc,
                            (Bitmap) message.obj, mGoodsDetailInfo.getSharePath());
        }
    };

    @Override
    public void returnOrderBenefit(OrderBenefitResponse orderBenefitResponse) {
        if (orderBenefitResponse == null) {
            return;
        }
        mOrderDetailViewModel.getOrderDetailInfo(context, orderBenefitResponse.salebill_id);
    }

    @Override
    public void returnOrderDetailInfo(GoodsDetailInfo goodsDetailInfo) {
        if (goodsDetailInfo == null) {
            return;
        }
        if (goodsDetailInfo.order_product != null) {
            goodsDetailInfo.sarti_id = goodsDetailInfo.order_product.sarti_id;
            goodsDetailInfo.sarti_name = goodsDetailInfo.order_product.sarti_name;
            goodsDetailInfo.sarti_saleprice = goodsDetailInfo.order_product.sarti_saleprice;
            goodsDetailInfo.pay_type = goodsDetailInfo.order_product.pay_type;
            goodsDetailInfo.sarti_point_price = goodsDetailInfo.order_product.sum_point;
        }
        ConfirmPayActivity.start(context, goodsDetailInfo);
    }

    @Override
    public void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse) {

    }

    @Override
    public void returnCancelOrder() {

    }

    @Override
    public void onRefreshData(Object object) {

    }

    @Override
    public void onLoadMoreData(Object object) {

    }

    @Override
    public void onNetError() {

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

    @Override
    public void returnOrderPayInfo(int optType, OrderPayInfoResponse orderPayInfoResponse) {

    }

    @Override
    public void returnOrderStatus(OrderStatusResponse orderStatusResponse) {

    }

    @Override
    public void returnFVipPay(int optType, OrderPayInfoResponse orderPayInfoResponse) {
        if (PayViewModel.CHECK_ORDER_STATUS == optType) {
            if (orderPayInfoResponse == null) {
                ToastUtil.showShortToast("支付成功");
                PayCompleteActivity.start(context, mSartiId, orderPayInfoResponse.order_no);
            } else {
                return;
            }
            return;
        }
        if (orderPayInfoResponse == null) {
            return;
        }
        if (TextUtils.equals(mPayType, ConfirmPayActivity.PAY_BY_WECHAT)) {
            String path = "/pages/order/appPay?" + "salebill_id="
                    + "&mem_phone=" + mUserInfo.mem_phone;
            new ShareUtils()
                    .init(context)
                    .jump2WxMiniProgram(path);
        } else {
            PayUtils payUtils = new PayUtils();
            payUtils.registerApp(context);
            payUtils.setOnPayListener(new OnPayListener() {
                @Override
                public void onPaySuccess() {

                }

                @Override
                public void onPayFailed() {
                    ToastUtil.showShortToast("支付失败");
                }
            });
            payUtils.startPay(GoodsDetailActivity.this, mPayType, CommandTools.beanToJson(orderPayInfoResponse));
        }
    }
}