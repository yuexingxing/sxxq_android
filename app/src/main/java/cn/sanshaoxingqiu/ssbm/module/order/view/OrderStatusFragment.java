package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.ContainerUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentOrderStatusBinding;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderListResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderDetailModel;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.OrderListAdapter;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderListViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.inquiry.view.AppointmentForConsultationActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import com.exam.commonbiz.util.ToastUtil;

/**
 * 待支付/待使用/已完成
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class OrderStatusFragment extends BaseFragment<OrderListViewModel, FragmentOrderStatusBinding> implements IOrderDetailModel, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private int mTotalCount;
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
        onRefresh();
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
                OrderDetailActivity.start(context, orderState, item.id);
            }

            @Override
            public void onSubscribe(OrderInfo item) {
                AppointmentForConsultationActivity.start(context);
            }

            @Override
            public void onViewCouponCode(OrderInfo item) {
//                ViewCouponCodeActivity.start(context);
                //TODO 查看券码先跳转到订单详情
                OrderDetailActivity.start(context, orderState, item.id);
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
                GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
                if (item.shopSartiInfo != null){
                    goodsDetailInfo.sarti_name = item.shopSartiInfo.sarti_name;
                }
                goodsDetailInfo.sarti_id = item.shopSartiInfo.sarti_id;
                goodsDetailInfo.salebill_id = item.id;
                goodsDetailInfo.sum_amt = item.totalPrice;
                goodsDetailInfo.sum_point = item.sumPoint;
                ConfirmPayActivity.start(context, goodsDetailInfo);
            }
        });

        mOrderListAdapter.disableLoadMoreIfNotFullPage();
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.emptyLayout.setOnButtonClick(view -> {
            onRefresh();
        });

        if (isVisiable) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        curPage = 1;
        if (mViewModel != null) {
            mViewModel.getOrderList(orderState, curPage, Constants.PAGE_SIZE);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        ++curPage;
        mViewModel.getOrderList(orderState, curPage, Constants.PAGE_SIZE);
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
        mTotalCount = orderListResponse.count;
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
        OrderListResponse orderListResponse = (OrderListResponse) object;
        if (ContainerUtil.isEmpty(orderListResponse.content)) {
            mOrderListAdapter.loadMoreEnd();
            return;
        }
        mOrderListAdapter.addData(orderListResponse.content);
        mOrderListAdapter.loadMoreComplete();
    }

    @Override
    public void onNetError() {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.emptyLayout.showError();
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