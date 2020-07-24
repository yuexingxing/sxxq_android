package com.sanshao.bs.module.personal.inquiry.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityToBeInquiryBinding;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.view.ViewCouponCodeActivity;
import com.sanshao.bs.module.personal.inquiry.adapter.ToBeInquiryAdapter;
import com.sanshao.bs.module.personal.inquiry.model.IInquiryModel;
import com.sanshao.bs.module.personal.inquiry.viewmodel.InquiryListViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.List;


/**
 * 待问诊列表
 *
 * @Author yuexingxing
 * @time 2020/7/24
 */
public class ToBeInquiryActivity extends BaseActivity<BaseViewModel, ActivityToBeInquiryBinding> implements IInquiryModel {

    private ToBeInquiryAdapter mToBeInquiryAdapter;
    private InquiryListViewModel mInquiryListViewModel;

    public static void start(Context context) {
        Intent starter = new Intent(context, ToBeInquiryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_be_inquiry;
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

        mInquiryListViewModel = new InquiryListViewModel(this);
        mToBeInquiryAdapter = new ToBeInquiryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mToBeInquiryAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setFocusable(false);
        mToBeInquiryAdapter.setOnItemClickListener(new ToBeInquiryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OrderInfo item) {
                InquiryDetailActivity.start(context);
            }

            @Override
            public void onViewCodeClick(OrderInfo item) {
                ViewCouponCodeActivity.start(context);
            }
        });

        mInquiryListViewModel.getInquiryList();
    }

    @Override
    public void returnInquiryList(List<OrderInfo> orderInfoList) {
        if (orderInfoList == null) {
            return;
        }

        mToBeInquiryAdapter.setNewData(orderInfoList);
    }
}