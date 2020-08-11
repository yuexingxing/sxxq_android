package com.sanshao.bs.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.log.XLog;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityGoodsListBinding;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.view.ConfirmOrderActivity;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsListModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GoodsListAdapter;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import com.sanshao.bs.module.shoppingcenter.viewmodel.GoodsListViewModel;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.OnItemEnterOrExitVisibleHelper;
import com.sanshao.bs.util.ShareUtils;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

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

    private String mArtiTagId;
    private int mPageNum = 0;
    private GoodsListAdapter mGoodsListAdapter;

    public static void start(Context context, String artiTagId) {
        Intent starter = new Intent(context, GoodsListActivity.class);
        starter.putExtra(Constants.OPT_DATA, artiTagId);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {

        mArtiTagId = getIntent().getStringExtra(Constants.OPT_DATA);
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
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            mPageNum = 0;
            mViewModel.getGoodsList(mArtiTagId, mPageNum, Constants.PAGE_SIZE);
        });

        mGoodsListAdapter = new GoodsListAdapter();
        mGoodsListAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void onBuyClick(GoodsDetailInfo goodsDetailInfo) {
                ConfirmOrderActivity.start(context, goodsDetailInfo.sarti_id);
            }

            @Override
            public void onGoToDetail(GoodsDetailInfo goodsDetailInfo) {
                GoodsDetailActivity.start(GoodsListActivity.this, goodsDetailInfo.sarti_id);
            }

            @Override
            public void onShareClick(GoodsDetailInfo goodsDetailInfo) {
                share(goodsDetailInfo);
            }

            @Override
            public void onConsultClick() {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.goodsListRecyclerView.setLayoutManager(linearLayoutManager);
        binding.goodsListRecyclerView.setAdapter(mGoodsListAdapter);
        mGoodsListAdapter.setEnableLoadMore(false);
        mGoodsListAdapter.setOnLoadMoreListener(this, binding.goodsListRecyclerView);
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
            mPageNum = 0;
            mViewModel.getGoodsList(mArtiTagId, mPageNum, Constants.PAGE_SIZE);
        });

        binding.ivToTop.setOnClickListener(view -> binding.goodsListRecyclerView.smoothScrollToPosition(0));
        OnItemEnterOrExitVisibleHelper helper = new OnItemEnterOrExitVisibleHelper();
        helper.setRecyclerScrollListener(binding.goodsListRecyclerView);
        helper.setOnScrollStatusListener(new OnItemEnterOrExitVisibleHelper.OnScrollStatusListener() {
            public void onSelectEnterPosition(int postion) {
                XLog.d(TAG, "进入Enter：" + postion);
                if (postion >= 6) {
                    binding.ivToTop.setVisibility(View.VISIBLE);
                } else {
                    binding.ivToTop.setVisibility(View.GONE);
                }
            }

            public void onSelectExitPosition(int postion) {
                XLog.d(TAG, "退出Exit：" + postion);
                if (postion >= mGoodsListAdapter.getData().size()) {
                    return;
                }
                mGoodsListAdapter.getData().get(postion).isPlay = false;
                mGoodsListAdapter.notifyItemChanged(postion);
            }
        });

        mGoodsListAdapter.setPreLoadNumber(1);
        mGoodsListAdapter.disableLoadMoreIfNotFullPage();
        mViewModel.getGoodsList(mArtiTagId, mPageNum, Constants.PAGE_SIZE);
    }

    @Override
    public void onLoadMoreRequested() {
        ++mPageNum;
        mViewModel.getGoodsList(mArtiTagId, mPageNum, Constants.PAGE_SIZE);
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

    private void share(GoodsDetailInfo goodsDetailInfo) {

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
                        new GoodsPosterDialog().show(context, goodsDetailInfo);
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
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            return;
        }
        List<GoodsDetailInfo> list = (List<GoodsDetailInfo>) object;
        if (ContainerUtil.isEmpty(list)) {
            binding.emptyLayout.showEmpty("暂无商品", R.drawable.image_nogoods);
            return;
        }
        mGoodsListAdapter.setNewData(list);
        binding.emptyLayout.showSuccess();
    }

    @Override
    public void onLoadMoreData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            return;
        }
        List<GoodsDetailInfo> list = (List<GoodsDetailInfo>) object;
        if (ContainerUtil.isEmpty(list)) {
            mGoodsListAdapter.loadMoreEnd();
            return;
        }
        mGoodsListAdapter.loadMoreComplete();
        mGoodsListAdapter.addData(list);
    }

    @Override
    public void onNetError() {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.emptyLayout.showEmpty("没有网络", R.drawable.image_servererror);
        binding.emptyLayout.showError();
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
}