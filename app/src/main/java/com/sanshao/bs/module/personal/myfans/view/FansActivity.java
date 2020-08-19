package com.sanshao.bs.module.personal.myfans.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.log.XLog;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityMyfansBinding;
import com.sanshao.bs.module.invitation.bean.UserReferrals;
import com.sanshao.bs.module.personal.myfans.model.IFansCallBack;
import com.sanshao.bs.module.personal.myfans.viewmodel.FansViewModel;
import com.sanshao.bs.util.OnItemEnterOrExitVisibleHelper;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FansActivity extends BaseActivity<FansViewModel, ActivityMyfansBinding> implements IFansCallBack {

    private FansListAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, FansActivity.class);
        context.startActivity(starter);
    }

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

        adapter = new FansListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.fansRecyclerView.setLayoutManager(linearLayoutManager);
        binding.fansRecyclerView.setAdapter(adapter);

        binding.ivToTop.setOnClickListener(view -> binding.fansRecyclerView.smoothScrollToPosition(0));
        OnItemEnterOrExitVisibleHelper helper = new OnItemEnterOrExitVisibleHelper();
        helper.setRecyclerScrollListener(binding.fansRecyclerView);
        helper.setOnScrollStatusListener(new OnItemEnterOrExitVisibleHelper.OnScrollStatusListener() {
            public void onSelectEnterPosition(int postion) {
                if (postion >= 10) {
                    binding.ivToTop.setVisibility(View.VISIBLE);
                } else {
                    binding.ivToTop.setVisibility(View.GONE);
                }
            }

            public void onSelectExitPosition(int postion) {

            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getFans(FansActivity.this);
            }
        });

        mViewModel.getFans(FansActivity.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myfans;
    }

    @Override
    public void showFans(List<UserReferrals.UserReferralsItem> fansList) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (ContainerUtil.isEmpty(fansList)) {
            binding.emptyLayout.showEmpty("暂无粉丝", R.drawable.image_nofans);
            return;
        }
        binding.emptyLayout.showSuccess();
        adapter.setNewData(fansList);
    }
}
