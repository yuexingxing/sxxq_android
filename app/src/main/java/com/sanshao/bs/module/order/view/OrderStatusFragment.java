package com.sanshao.bs.module.order.view;

import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentOrderStatusBinding;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.bean.OrderListResponse;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
import com.sanshao.bs.module.order.model.IOrderDetailModel;
import com.sanshao.bs.module.order.view.adapter.OrderListAdapter;
import com.sanshao.bs.module.order.viewmodel.OrderListViewModel;
import com.sanshao.bs.module.personal.inquiry.view.AppointmentForConsultationActivity;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.ToastUtil;

import java.util.List;

/**
 * 待支付/待使用/已完成
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class OrderStatusFragment extends BaseFragment<OrderListViewModel, FragmentOrderStatusBinding> implements IOrderDetailModel, BaseQuickAdapter.RequestLoadMoreListener {

    private int curPage = 1;
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
    protected void loadData() {
        curPage = 1;
        if (mViewModel != null) {
            mViewModel.getOrderList(orderState, curPage, Constants.PAGE_SIZE);
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
        binding.recyclerView.setNestedScrollingEnabled(true);
        mOrderListAdapter.setOnLoadMoreListener(this, binding.recyclerView);
        mOrderListAdapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onOrderDetail(OrderInfo item) {
                OrderDetailActivity.start(context, item.id);
            }

            @Override
            public void onSubscribe(OrderInfo item) {
                AppointmentForConsultationActivity.start(context);
            }

            @Override
            public void onViewCouponCode(OrderInfo item) {
//                ViewCouponCodeActivity.start(context);
                //TODO 查看券码先跳转到订单详情
                OrderDetailActivity.start(context, item.id);
            }

            @Override
            public void onDeleteOrder(int position, OrderInfo item) {
                mOrderListAdapter.remove(position);
            }

            @Override
            public void onCancelOrder(int position, OrderInfo item) {
                mViewModel.cancelOrder(item.id);
            }

            @Override
            public void onPay(OrderInfo item) {

            }
        });

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                curPage = 1;
                mViewModel.getOrderList(orderState, curPage, Constants.PAGE_SIZE);
            }
        });
        binding.emptyLayout.bindView(binding.recyclerView);
        binding.emptyLayout.setOnButtonClick(view -> {

        });

        if (isVisiable) {
            loadData();
        }
    }

    @Override
    public void onRefreshData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            binding.emptyLayout.showEmpty("暂无数据", R.drawable.imsge_noorder);
            return;
        }
        OrderListResponse orderListResponse = (OrderListResponse) object;
        if (ContainerUtil.isEmpty(orderListResponse.content)) {
            binding.emptyLayout.showEmpty("暂无数据", R.drawable.imsge_noorder);
            return;
        }
        mOrderListAdapter.setNewData(orderListResponse.content);
        binding.emptyLayout.showSuccess();
    }

    @Override
    public void onLoadMoreData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            mOrderListAdapter.loadMoreEnd();
            return;
        }

        List<OrderInfo> list = (List<OrderInfo>) object;
        if (ContainerUtil.isEmpty(list)) {
            mOrderListAdapter.loadMoreEnd();
            return;
        }
        mOrderListAdapter.addData(list);
        mOrderListAdapter.loadMoreComplete();
    }

    @Override
    public void onNetError() {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.emptyLayout.showError();
    }

    @Override
    public void onLoadMoreRequested() {
        ++curPage;
        mViewModel.getOrderList(orderState, curPage, Constants.PAGE_SIZE);
    }

    @Override
    public void returnOrderDetailInfo(GoodsDetailInfo orderDetailResponse) {

    }

    @Override
    public void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse) {

    }

    @Override
    public void returnCancelOrder() {
        ToastUtil.showShortToast("取消成功");
        loadData();
    }
}