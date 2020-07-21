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
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityGoodsListBinding;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.view.ConfirmOrderActivity;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsListModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GoodsListAdapter;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsDetailShareDialog;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import com.sanshao.bs.module.shoppingcenter.viewmodel.GoodsListViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 商品列表
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsListActivity extends BaseActivity<GoodsListViewModel, ActivityGoodsListBinding> implements BaseQuickAdapter.RequestLoadMoreListener,
        IGoodsListModel {

    private GoodsListAdapter mGoodsListAdapter;
    private GoodsListViewModel mGoodsListViewModel;

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

        mGoodsListViewModel = new GoodsListViewModel();
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
                new GoodsDetailShareDialog().show(context, (postion, object) -> {
                    if (postion == 0) {

                    } else if (postion == 1) {
                        new GoodsPosterDialog().show(context);
                    }
                });
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
        mGoodsListViewModel.getGoodsList(this);
        mGoodsListAdapter.setEnableLoadMore(false);
    }

    @Override
    public void onRefreshData(List<GoodsDetailInfo> list){
        if (ContainerUtil.isEmpty(list)){
            return;
        }
        mGoodsListAdapter.addData(list);
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
        if (payStatusChangedEvent == null){
            return;
        }
        if (payStatusChangedEvent.paySuccess){
            finish();
        }
    }
}