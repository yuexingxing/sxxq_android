package com.sanshao.bs.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.AliPlayerFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityGoodsDetailBinding;
import com.sanshao.bs.module.EmptyWebViewActivity;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.view.ConfirmOrderActivity;
import com.sanshao.bs.module.order.view.adapter.TabFragmentPagerAdapter;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.ResponseGoodsDetail;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsDetailModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.SetMealAdapter;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsInroductionDialog;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import com.sanshao.bs.module.shoppingcenter.viewmodel.GoodsDetailViewModel;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.ShareUtils;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailViewModel, ActivityGoodsDetailBinding> implements IGoodsDetailModel {
    private final String TAG = GoodsDetailActivity.class.getSimpleName();
    private GoodsDetailViewModel mGoodsDetailViewModel;
    private SetMealAdapter mSetMealAdapter;
    private GoodsDetailInfo mGoodsDetailInfo;
    private List<Fragment> mFragmentList;
    private FragmentPagerAdapter mFragmentPagerAdapter;

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

        initViewPager();
        mGoodsDetailViewModel = new GoodsDetailViewModel(this);
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
            if (scrollY > ScreenUtil.dp2px(context, 200)) {
                binding.llTab.setVisibility(View.VISIBLE);
            } else {
                binding.llTab.setVisibility(View.GONE);
            }
        });

        binding.llIntroduction.setOnClickListener(v -> new GoodsInroductionDialog().show(context, "玻尿酸（Hyaluronan）是由D-葡萄糖醛酸及N-乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构双糖单位又称糖醛酸、透明质酸基本结构。乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类。玻尿酸（Hyaluronan）是由D-葡萄糖醛酸及N-乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构双糖单位又称糖醛酸、透明质酸基本结构。乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类。玻尿酸（Hyaluronan）是由D-葡萄糖醛酸及N-乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构双糖单位又称糖醛酸、透明质酸基本结构。乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类。玻尿酸（Hyaluronan）是由D-葡萄糖醛酸及N-乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构双糖单位又称糖醛酸、透明质酸基本结构。乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类。玻尿酸（Hyaluronan）是由D-葡萄糖醛酸及N-乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构双糖单位又称糖醛酸、透明质酸基本结构。乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类。玻尿酸（Hyaluronan）是由D-葡萄糖醛酸及N-乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构双糖单位又称糖醛酸、透明质酸基本结构。乙酰葡糖胺组成的双糖单位又称糖醛酸、透明质酸基本结构是由两个双糖单位D-葡萄糖醛酸及N-乙酰葡糖胺组成的大型多糖类。"));

        binding.includeBottom.btnBuy.setOnClickListener(v -> ConfirmOrderActivity.start(context));
        binding.llTabGoods.setOnClickListener(v -> {
            initTabStatus(0);
            scrollToView(binding.llGoods);
        });
        binding.llTabGoodsDetail.setOnClickListener(v -> {
            initTabStatus(1);
            binding.nestedScrollview.smoothScrollTo(0, binding.llGoodsDetail.getTop() - ScreenUtil.dp2px(context, 70));
        });

        mSetMealAdapter = new SetMealAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewSetmeal.setLayoutManager(linearLayoutManager);
        binding.recyclerViewSetmeal.setAdapter(mSetMealAdapter);
        binding.recyclerViewSetmeal.setNestedScrollingEnabled(false);
        binding.recyclerViewSetmeal.setFocusable(false);
        ((DefaultItemAnimator) binding.recyclerViewSetmeal.getItemAnimator()).setSupportsChangeAnimations(false);
        mSetMealAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailInfo goodsTypeDetailInfo = mSetMealAdapter.getData().get(position);
                goodsTypeDetailInfo.checked = !goodsTypeDetailInfo.checked;
                adapter.notifyItemChanged(position);
            }
        });

        binding.includeBottom.llShare.setOnClickListener(v -> {
            share();
        });
        binding.ivRecommendReward.setOnClickListener(v ->
                EmptyWebViewActivity.start(context, "http://www.2345.com")
        );
        binding.ivCallPhone.setOnClickListener(view -> CommandTools.callPhone(context, "12345678"));
        mGoodsDetailViewModel.getGoodsDetail();
    }

    private void initViewPager() {

        //把Fragment添加到List集合里面
        mFragmentList = new ArrayList<>();
        mFragmentList.add(GoodsDetailVideoFragment.newInstance());
        mFragmentList.add(GoodsDetailPictureFragment.newInstance());
        mFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        binding.viewPagerVideo.setAdapter(mFragmentPagerAdapter);
        binding.viewPagerVideo.setOffscreenPageLimit(mFragmentList.size());
        binding.viewPagerVideo.setCurrentItem(0);
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
        commonDialogInfoList.add(new CommonDialogInfo("生成海报"));

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
                        new GoodsPosterDialog().show(context);
                    }
                })
                .show();
    }

    @Override
    public void returnGoodsDetail(ResponseGoodsDetail responseGoodsDetail) {
        if (responseGoodsDetail == null) {
            return;
        }
        mGoodsDetailInfo = responseGoodsDetail.goodsDetailInfo;

        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        binding.tryMatching.initData();
        binding.tryMatching.initViewPager(getSupportFragmentManager());

        mSetMealAdapter.setNewData(responseGoodsDetail.setMealList);
    }
}