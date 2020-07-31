package com.sanshao.bs.module.order.view;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentOrderStatusBinding;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.model.IOrderModel;
import com.sanshao.bs.module.order.view.adapter.OrderListAdapter;
import com.sanshao.bs.module.order.viewmodel.OrderListViewModel;
import com.sanshao.bs.module.order.viewmodel.OrderStatusViewModel;
import com.sanshao.bs.module.personal.inquiry.view.AppointmentForConsultationActivity;

import java.util.List;

/**
 * 待支付/待使用/已完成
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class OrderStatusFragment extends BaseFragment<OrderListViewModel, FragmentOrderStatusBinding> implements IOrderModel {

    private int orderState;
    private OrderListAdapter mOrderListAdapter;

    public OrderStatusFragment() {

    }

    public static OrderStatusFragment newInstance(int orderState) {
        OrderStatusFragment fragment = new OrderStatusFragment();
        Bundle args = new Bundle();
        args.putInt(OrderInfo.ORDER_STATE, orderState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderState = getArguments().getInt(OrderInfo.ORDER_STATE);
        }
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
        mOrderListAdapter = new OrderListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mOrderListAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setFocusable(false);
        mOrderListAdapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onOrderDetail(OrderInfo item) {
                OrderDetailActivity.start(context);
            }

            @Override
            public void onSubscribe(OrderInfo item) {
                AppointmentForConsultationActivity.start(context);
            }

            @Override
            public void onViewCouponCode(OrderInfo item) {
                ViewCouponCodeActivity.start(context);
            }

            @Override
            public void onDeleteOrder(int position, OrderInfo item) {
                mOrderListAdapter.remove(position);
            }

            @Override
            public void onCancelOrder(int position, OrderInfo item) {

            }

            @Override
            public void onPay(OrderInfo item) {
                ConfirmPayActivity.start(context);
            }
        });
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getOrderList(orderState);
            }
        });
        binding.emptyLayout.bindView(binding.recyclerView);
        binding.emptyLayout.setOnButtonClick(view -> {
            mViewModel.getOrderList(orderState);
        });
        mViewModel.getOrderList(orderState);
    }

    @Override
    public void onRefreshData(Object object) {
        if (object == null) {
            return;
        }
        List<OrderInfo> list = (List<OrderInfo>) object;
        mOrderListAdapter.getData().clear();
        binding.swipeRefreshLayout.setRefreshing(false);
        if (ContainerUtil.isEmpty(list)) {
            binding.emptyLayout.showEmpty("暂无数据", R.drawable.image_logo);
            return;
        }
        mOrderListAdapter.addData(list);
    }

    @Override
    public void onLoadMoreData(Object object) {

    }

    @Override
    public void onNetError() {
        binding.emptyLayout.showError();
    }
}