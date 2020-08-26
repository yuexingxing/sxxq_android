package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityIncomeHomeBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.IncomeHomeViewModel;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import androidx.fragment.app.FragmentTransaction;

public class IncomeHomeActivity extends BaseActivity<IncomeHomeViewModel, ActivityIncomeHomeBinding> {

    private static final int TAB_INCOME = 1;
    private static final int TAB_RANKINGLIST = 2;

    private IncomeFrament incomeFrament;
    private RankinglistFragment rankinglistFragment;

    public static void start(Context context) {
        Intent starter = new Intent(context, IncomeHomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_income_home;
    }

    @Override
    public void initData() {
        initListener();
        changeTab(TAB_INCOME);
    }

    private void initListener() {
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
        binding.ivRankinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(TAB_RANKINGLIST);
            }
        });
        binding.ivIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(TAB_INCOME);
            }
        });
    }

    private void changeTab(int tab) {
        if (tab == TAB_INCOME && !binding.ivIncome.isEnabled()) {
            return;
        }
        if (tab == TAB_RANKINGLIST && !binding.ivRankinglist.isEnabled()) {
            return;
        }
        switch (tab) {
            case TAB_INCOME:
                binding.ivIncome.setEnabled(false);
                binding.ivRankinglist.setEnabled(true);
                break;
            case TAB_RANKINGLIST:
                binding.ivIncome.setEnabled(true);
                binding.ivRankinglist.setEnabled(false);
                break;
        }
        changeTitle(tab);
        changeContent(tab);
    }

    private void changeContent(int tab) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (tab) {
            case TAB_INCOME:
                if (incomeFrament == null) {
                    incomeFrament = IncomeFrament.newInstance();
                    transaction.add(R.id.income_home_content, incomeFrament);
                } else {
                    transaction.show(incomeFrament);
                }
                break;
            case TAB_RANKINGLIST:
                if (rankinglistFragment == null) {
                    rankinglistFragment = RankinglistFragment.newInstance();
                    transaction.add(R.id.income_home_content, rankinglistFragment);
                } else {
                    transaction.show(rankinglistFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void changeTitle(int tab) {
        binding.titleBar.setTitle(tab == TAB_INCOME ? R.string.income : R.string.rankinglist);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (incomeFrament != null) {
            transaction.hide(incomeFrament);
        }
        if (rankinglistFragment != null) {
            transaction.hide(rankinglistFragment);
        }
    }
}
