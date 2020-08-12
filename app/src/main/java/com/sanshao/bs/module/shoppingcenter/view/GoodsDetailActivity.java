package com.sanshao.bs.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityGoodsDetailBinding;
import com.sanshao.bs.module.EmptyWebViewActivity;
import com.sanshao.bs.module.home.model.BannerInfo;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.view.ConfirmOrderActivity;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.VideoInfo;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsDetailModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.SetMealAdapter;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsInroductionDialog;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import com.sanshao.bs.module.shoppingcenter.viewmodel.GoodsDetailViewModel;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.MathUtil;
import com.sanshao.bs.util.ShareUtils;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

/**
 * 商品详情
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailViewModel, ActivityGoodsDetailBinding> implements IGoodsDetailModel {
    private final String TAG = GoodsDetailActivity.class.getSimpleName();
    private String mSartiId;
    private SetMealAdapter mSetMealAdapter;
    private GoodsDetailInfo mGoodsDetailInfo;

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

        mSartiId = getIntent().getStringExtra(Constants.OPT_DATA);
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

        binding.emptyLayout.setOnButtonClick(view -> mViewModel.getGoodsDetail(mSartiId));
        binding.nestedScrollview.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > ScreenUtil.dp2px(context, 200)) {
                binding.llTab.setVisibility(View.VISIBLE);
            } else {
                binding.llTab.setVisibility(View.GONE);
            }
        });

        binding.llIntroduction.setOnClickListener(v -> {
            if (mGoodsDetailInfo == null) {
                return;
            }
            new GoodsInroductionDialog().show(context, "商品说明", mGoodsDetailInfo.sarti_intro);
        });

        binding.includeBottom.btnBuy.setOnClickListener(v -> ConfirmOrderActivity.start(context, mSartiId));
        binding.llTabGoods.setOnClickListener(v -> {
            initTabStatus(0);
            scrollToView(binding.llGoods);
        });
        binding.llTabGoodsDetail.setOnClickListener(v -> {
            initTabStatus(1);
            binding.nestedScrollview.smoothScrollTo(0, binding.llGoodsDetail.getTop() - ScreenUtil.dp2px(context, 60));
        });
        binding.homeBannerLayout.setDotGravity();

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
        binding.ivRecommendReward.setOnClickListener(v ->
                new GoodsInroductionDialog().show(context, "邀请有礼 一起变美", getResources().getString(R.string.goods_detail_invite_tip))
        );
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.homeBannerLayout.getLayoutParams();
        int videoHeight = ScreenUtil.getScreenWidth(SSApplication.app) - ScreenUtil.dp2px(SSApplication.app, 24);
        layoutParams.height = videoHeight;
        binding.homeBannerLayout.setLayoutParams(layoutParams);
        binding.ivCallPhone.setOnClickListener(view -> CommandTools.callPhone(context, "12345678"));
        mViewModel.getGoodsDetail(mSartiId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    private void scrollToView(View view) {
        binding.nestedScrollview.post(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                int offset = location[1] - binding.nestedScrollview.getMeasuredHeight();
                if (offset < 0) {
                    offset = 0;
                }
                binding.nestedScrollview.fling(location[1]);
                binding.nestedScrollview.smoothScrollTo(0, location[1]);
            }
        });
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
                        ShareUtils.shareText(GoodsDetailActivity.this, "title", SHARE_MEDIA.WEIXIN, new CommonCallBack() {
                            @Override
                            public void callback(int postion, Object object) {

                            }
                        });
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
        binding.rlBottomView.setVisibility(View.VISIBLE);
        binding.emptyLayout.showSuccess();
        mGoodsDetailInfo = goodsDetailInfo;

        binding.tvTitle.setText(goodsDetailInfo.sarti_name);
        binding.tvPrice.setText(MathUtil.getNumExclude0(goodsDetailInfo.sarti_saleprice));
        binding.tvOldPrice.setText("¥" + MathUtil.getNumExclude0(goodsDetailInfo.sarti_mkprice));
        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        binding.tvSellNum.setText("已售" + goodsDetailInfo.sell_num);

        if (goodsDetailInfo.isFree()) {
            binding.tvPrice.setText("免费领取");
            binding.includeBottom.btnBuy.setText("免费领取");
        } else if (goodsDetailInfo.isPayByPoint()) {
            binding.tvPrice.setText(goodsDetailInfo.getPointTip());
            binding.tvOldPrice.setVisibility(View.GONE);
            binding.includeBottom.btnBuy.setText("分享金购买");
        } else {
            binding.tvPrice.setText("¥" + MathUtil.getNumExclude0(goodsDetailInfo.sarti_saleprice));
        }

        RichText.from(goodsDetailInfo.sarti_desc).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(binding.tvGoodsDetail);

        if (!ContainerUtil.isEmpty(goodsDetailInfo.product_list)) {
            //0=非套餐，1=套餐
            if (goodsDetailInfo.is_package == 1) {
                binding.tryMatching.setVisibility(View.GONE);
                binding.llSetMeal.setVisibility(View.VISIBLE);
                mSetMealAdapter.setNewData(goodsDetailInfo.product_list);
            } else {
                binding.llSetMeal.setVisibility(View.GONE);
                binding.tryMatching.setVisibility(View.VISIBLE);
                binding.tryMatching.setData(goodsDetailInfo.product_list);
                binding.tryMatching.initViewPager(getSupportFragmentManager());
            }
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
}