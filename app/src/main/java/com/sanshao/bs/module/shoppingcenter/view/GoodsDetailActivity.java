package com.sanshao.bs.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Res;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityGoodsDetailBinding;
import com.sanshao.bs.module.EmptyWebViewActivity;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.view.ConfirmOrderActivity;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.view.adapter.SetMealAdapter;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsDetailShareDialog;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsInroductionDialog;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import com.sanshao.bs.module.shoppingcenter.viewmodel.GoodsDetailViewModel;
import com.sanshao.bs.util.Constants;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 商品详情
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailViewModel, ActivityGoodsDetailBinding> {
    private final String TAG = GoodsDetailActivity.class.getSimpleName();
    private SetMealAdapter mSetMealAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, GoodsDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {

        binding.includeVideo.ivIcon.setVisibility(View.GONE);
        binding.includeVideo.videoPlayLayout.setVisibility(View.VISIBLE);
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

        binding.nestedScrollview.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > 200) {
                binding.llTab.setVisibility(View.VISIBLE);
            } else {
                binding.llTab.setVisibility(View.GONE);
            }
        });

        binding.llIntroduction.setOnClickListener(v -> new GoodsInroductionDialog().show(context));

        binding.includeBottom.btnBuy.setOnClickListener(v -> ConfirmOrderActivity.start(context));
        binding.llTabGoods.setOnClickListener(v -> {
            initTabStatus(0);
            scrollToView(binding.llGoods);
        });
        binding.llTabGoodsDetail.setOnClickListener(v -> {
            initTabStatus(1);
            scrollToView(binding.llGoodsDetail);
        });

        mSetMealAdapter = new SetMealAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewSetmeal.setLayoutManager(linearLayoutManager);
        binding.recyclerViewSetmeal.setAdapter(mSetMealAdapter);
        binding.recyclerViewSetmeal.setNestedScrollingEnabled(false);
        binding.recyclerViewSetmeal.setFocusable(false);
        ((DefaultItemAnimator)  binding.recyclerViewSetmeal.getItemAnimator()).setSupportsChangeAnimations(false);
        mSetMealAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailInfo goodsTypeDetailInfo = mSetMealAdapter.getData().get(position);
                goodsTypeDetailInfo.checked = !goodsTypeDetailInfo.checked;
                adapter.notifyItemChanged(position);
            }
        });

        for (int i = 0; i < 2; i++) {
            GoodsDetailInfo goodsTypeDetailInfo = GoodsDetailInfo.getGoodsDetailInfo();
            goodsTypeDetailInfo.name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            mSetMealAdapter.addData(goodsTypeDetailInfo);
        }

        binding.includeBottom.llShare.setOnClickListener(v -> {
            new GoodsDetailShareDialog().show(context, (postion, object) -> {
                if (postion == 0) {

                } else if (postion == 1) {
                    new GoodsPosterDialog().show(context);
                }
            });
        });
        binding.ivRecommendReward.setOnClickListener(v ->
                EmptyWebViewActivity.start(context, "http://www.2345.com")
        );

        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        binding.tryMatching.initData();
        binding.tryMatching.initViewPager(getSupportFragmentManager());
        binding.includeVideo.videoPlayLayout.setVideoPlayUrl(Constants.VIDEO_PLAY_URL);
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

    @Override
    protected void onPause() {
        super.onPause();
        binding.includeVideo.videoPlayLayout.pausePlay();
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
        if (payStatusChangedEvent == null){
            return;
        }
        if (payStatusChangedEvent.paySuccess){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.includeVideo.videoPlayLayout.pausePlay();
    }
}