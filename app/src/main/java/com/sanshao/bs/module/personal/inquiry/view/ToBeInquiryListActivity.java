package com.sanshao.bs.module.personal.inquiry.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityToBeInquiryListBinding;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.view.ViewCouponCodeActivity;
import com.sanshao.bs.module.personal.inquiry.adapter.ToBeInquiryListAdapter;
import com.sanshao.bs.module.personal.inquiry.model.IInquiryModel;
import com.sanshao.bs.module.personal.inquiry.viewmodel.ToBeInquiryListViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.List;

/**
 * 待问诊列表
 *
 * @Author yuexingxing
 * @time 2020/7/24
 */
public class ToBeInquiryListActivity extends BaseActivity<BaseViewModel, ActivityToBeInquiryListBinding> implements IInquiryModel {

    private ToBeInquiryListAdapter mToBeInquiryListAdapter;
    private ToBeInquiryListViewModel mToBeInquiryListViewModel;

    public static void start(Context context) {
        Intent starter = new Intent(context, ToBeInquiryListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_be_inquiry_list;
    }

    @Override
    public void initData() {

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

        mToBeInquiryListViewModel = new ToBeInquiryListViewModel(this);
        mToBeInquiryListAdapter = new ToBeInquiryListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mToBeInquiryListAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setFocusable(false);
        mToBeInquiryListAdapter.setOnItemClickListener(new ToBeInquiryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OrderInfo item) {
                ToBeInquiryDetailActivity.start(context);
            }

            @Override
            public void onViewCodeClick(OrderInfo item) {
                ViewCouponCodeActivity.start(context);
            }
        });

        mToBeInquiryListViewModel.getInquiryList();
    }

    @Override
    public void returnInquiryList(List<OrderInfo> orderInfoList) {
        if (orderInfoList == null) {
            return;
        }

        mToBeInquiryListAdapter.setNewData(orderInfoList);
    }

    @Override
    public void returnInquiryDetail(OrderInfo orderInfo) {

    }
}