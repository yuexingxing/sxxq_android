package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityIncomeRecordBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeViewCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter.IncomeRecordAdapter;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.IncomeViewModel;

/**
 * 进账记录
 *
 * @Author yuexingxing
 * @time 2020/10/13
 */
public class IncomeRecordActivity extends BaseActivity<IncomeViewModel, ActivityIncomeRecordBinding> implements IncomeViewCallBack {

    private IncomeRecordAdapter mIncomeRecordAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, IncomeRecordActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_income_record;
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
        mIncomeRecordAdapter = new IncomeRecordAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mIncomeRecordAdapter);

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }

            @Override
            public void onTitleClick(View view) {

            }

            @Override
            public void onRightClick(View view) {

            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();
    }

    private void getData() {
        LoadDialogMgr.getInstance().show(context);
        mViewModel.getIncomeList();
    }

    @Override
    public void requestIncomeInfoSucc(IncomeBean bean) {

    }

    @Override
    public void withdraw() {

    }

    @Override
    public void returnIncomeRecordList(List<IncomeInfo> incomeInfoList) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (ContainerUtil.isEmpty(incomeInfoList)) {
            binding.emptyLayout.showEmpty("暂无数据", R.drawable.imsge_noimages);
            return;
        }
        binding.emptyLayout.showSuccess();
        mIncomeRecordAdapter.addData(incomeInfoList);
    }

    @Override
    public void returnWithdrawRecordList(List<WithdrawInfo> withdrawInfoList) {

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