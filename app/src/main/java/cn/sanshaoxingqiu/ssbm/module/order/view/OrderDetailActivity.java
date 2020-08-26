package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityOrderDetailBinding;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderDetailModel;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.ConfirmOrderAdapter;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.RemainingServiceAdapter;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.ServedAdapter;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderDetailViewModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.Constants;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailViewModel, ActivityOrderDetailBinding> implements IOrderDetailModel {

    private String mSalebillId;
    private ServedAdapter mServedAdapter;
    private RemainingServiceAdapter mRemainingServiceAdapter;

    public static void start(Context context, String salebillId) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        starter.putExtra(Constants.OPT_DATA, salebillId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initData() {

        mSalebillId = getIntent().getStringExtra(Constants.OPT_DATA);
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

        mServedAdapter = new ServedAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewServed.setLayoutManager(linearLayoutManager);
        binding.recyclerViewServed.setAdapter(mServedAdapter);
        binding.recyclerViewServed.setNestedScrollingEnabled(false);
        binding.recyclerViewServed.setFocusable(false);

        mRemainingServiceAdapter = new RemainingServiceAdapter();
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewRemainingService.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewRemainingService.setAdapter(mRemainingServiceAdapter);
        binding.recyclerViewRemainingService.setNestedScrollingEnabled(false);
        binding.recyclerViewRemainingService.setFocusable(false);
        binding.tvCopy.setOnClickListener(view -> CommandTools.copyToClipboard(context, binding.tvOrderNo.getText().toString()));
        binding.llOrderMore.setOnClickListener(view -> {
            if (binding.recyclerViewServed.getVisibility() == View.GONE) {
                binding.ivMore.setImageResource(R.drawable.icon_goods_detail_more);
                binding.recyclerViewServed.setVisibility(View.VISIBLE);
                binding.recyclerViewRemainingService.setVisibility(View.VISIBLE);
            } else {
                binding.ivMore.setImageResource(R.drawable.icon_goods_detail_down);
                binding.recyclerViewServed.setVisibility(View.GONE);
                binding.recyclerViewRemainingService.setVisibility(View.GONE);
            }
        });

        binding.llCall.setOnClickListener(view -> {
            CommandTools.showCall(context);
        });

        binding.mulitySetMealView.setFragmentManager(getSupportFragmentManager());
        binding.mulitySetMealView.setOptType(ConfirmOrderAdapter.OPT_TYPE_ORDER_DETAIL);
        mViewModel.getOrderDetailInfo(context, mSalebillId);
        binding.guessYouLoveView.getData();
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

    @Override
    public void returnOrderDetailInfo(GoodsDetailInfo orderDetailResponse) {
        if (orderDetailResponse == null) {
            return;
        }
//        mServedAdapter.addData(orderDetailResponse.serverList);
//        mRemainingServiceAdapter.addData(orderDetailResponse.remainingServerList);
//        binding.mulitySetMealView.mConfirmOrderAdapter.addData(orderDetailResponse.goodsDetailInfo);

        List<GoodsDetailInfo> goodsDetailInfoList = new ArrayList<>();
        goodsDetailInfoList.add(orderDetailResponse);
        binding.mulitySetMealView.setData(goodsDetailInfoList);

        binding.tvOrderNo.setText(orderDetailResponse.salebill_id);
        binding.tvOrderTime.setText(orderDetailResponse.create_date);
        binding.tvPayType.setText(orderDetailResponse.pay_type + "");
        binding.tvPayTime.setText(orderDetailResponse.optr_date + "");
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
}