package cn.sanshaoxingqiu.ssbm.module.personal.myfans.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityMyfansBinding;

import com.exam.commonbiz.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.myfans.model.IFansCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.myfans.viewmodel.FansViewModel;
import cn.sanshaoxingqiu.ssbm.util.OnItemEnterOrExitVisibleHelper;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.ContainerUtil;
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
    public void showFans(List<UserInfo> fansList) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (ContainerUtil.isEmpty(fansList)) {
            binding.emptyLayout.showEmpty("暂无粉丝", R.drawable.image_nofans);
            return;
        }
        binding.emptyLayout.showSuccess();
        adapter.setNewData(fansList);
    }
}
