package com.sanshao.bs.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.log.XLog;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityGoodsListBinding;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.view.ConfirmOrderActivity;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsListModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GoodsListAdapter;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import com.sanshao.bs.module.shoppingcenter.viewmodel.GoodsListViewModel;
import com.sanshao.bs.util.ShareUtils;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

/**
 * 商品列表
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsListActivity extends BaseActivity<GoodsListViewModel, ActivityGoodsListBinding> implements BaseQuickAdapter.RequestLoadMoreListener,
        IGoodsListModel {

    private GoodsListAdapter mGoodsListAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, GoodsListActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {

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

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> mViewModel.getGoodsList());

        mGoodsListAdapter = new GoodsListAdapter();
        mGoodsListAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void onBuyClick() {
                ConfirmOrderActivity.start(context);
            }

            @Override
            public void onGoToDetail() {
                GoodsDetailActivity.start(GoodsListActivity.this);
            }

            @Override
            public void onShareClick() {
                share();
            }

            @Override
            public void onConsultClick() {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.goodsListRecyclerView.setLayoutManager(linearLayoutManager);
        binding.goodsListRecyclerView.setAdapter(mGoodsListAdapter);
        binding.goodsListRecyclerView.setNestedScrollingEnabled(false);
        binding.goodsListRecyclerView.setFocusable(false);
        mGoodsListAdapter.setOnLoadMoreListener(this, binding.goodsListRecyclerView);
        mGoodsListAdapter.setEnableLoadMore(false);
        binding.goodsListRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd == null || jzvd.jzDataSource == null) {
                    return;
                }
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
        binding.emptyLayout.bindView(binding.goodsListRecyclerView);
        binding.emptyLayout.setOnButtonClick(view -> {
            mViewModel.getGoodsList();
        });
        binding.nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d(TAG, "dy-" + scrollY + "/" + oldScrollY);
                if (scrollY > ScreenUtil.dp2px(context, 1000)) {
                    binding.ivToTop.setVisibility(View.VISIBLE);
                } else {
                    binding.ivToTop.setVisibility(View.GONE);
                }
            }
        });

        binding.ivToTop.setOnClickListener(view -> {
            binding.nestedScrollview.smoothScrollTo(0, 0);
        });
        mViewModel.getGoodsList();
    }

    @Override
    public void onLoadMoreRequested() {

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
                        ShareUtils.shareText(GoodsListActivity.this, "title", SHARE_MEDIA.WEIXIN, new CommonCallBack() {
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
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onRefreshData(Object object) {
        List<GoodsDetailInfo> list = (List<GoodsDetailInfo>) object;
        mGoodsListAdapter.getData().clear();
        binding.swipeRefreshLayout.setRefreshing(false);
        if (ContainerUtil.isEmpty(list)) {
            binding.emptyLayout.showEmpty("数据为空", R.drawable.image_logo);
            return;
        }
        mGoodsListAdapter.addData(list);
        binding.emptyLayout.showSuccess();
    }

    @Override
    public void onLoadMoreData(Object object) {

    }

    @Override
    public void onNetError() {
//        binding.emptyLayout.showError();
    }
}