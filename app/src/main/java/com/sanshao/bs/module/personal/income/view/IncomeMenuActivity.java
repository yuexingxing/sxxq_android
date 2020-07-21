package com.sanshao.bs.module.personal.income.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.util.Res;
import com.google.android.material.tabs.TabLayout;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityIncomeMenuBinding;
import com.sanshao.bs.module.personal.income.bean.IncomeMenuInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 进账菜单
 *
 * @Author yuexingxing
 * @time 2020/7/13
 */
public class IncomeMenuActivity extends BaseActivity<BaseViewModel, ActivityIncomeMenuBinding> {

    private List<Fragment> mFragmentList;
    private IncomeTabFragmentAdapter mIncomeTabFragmentAdapter;
    private List<IncomeMenuInfo> mIncomeMenuInfoList;

    public static void start(Context context) {
        Intent starter = new Intent(context, IncomeMenuActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_income_menu;
    }

    @Override
    public void initData() {
        initViewPager();
        initTabLayout();

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
    }

    private void initViewPager() {

        mIncomeMenuInfoList = new ArrayList<>();
        IncomeMenuInfo incomeMenuInfoSort = new IncomeMenuInfo();
        incomeMenuInfoSort.tilte = "排行榜";
        incomeMenuInfoSort.iconSelect = R.drawable.beautiful_press;
        incomeMenuInfoSort.iconUnSelect = R.drawable.beautiful;

        IncomeMenuInfo incomeMenuInfo = new IncomeMenuInfo();
        incomeMenuInfo.tilte = "进账";
        incomeMenuInfo.iconSelect = R.drawable.beautiful_press;
        incomeMenuInfo.iconUnSelect = R.drawable.beautiful;

        mIncomeMenuInfoList.add(incomeMenuInfoSort);
        mIncomeMenuInfoList.add(incomeMenuInfo);

        //把Fragment添加到List集合里面
        mFragmentList = new ArrayList<>();
        mFragmentList.add(RankingFragment.newInstance());
        mFragmentList.add(IncomeFragment.newInstance());
        mIncomeTabFragmentAdapter = new IncomeTabFragmentAdapter(getSupportFragmentManager(), mFragmentList, mIncomeMenuInfoList, context);
        binding.viewPager.setAdapter(mIncomeTabFragmentAdapter);
        binding.viewPager.setOffscreenPageLimit(mFragmentList.size());
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mIncomeTabFragmentAdapter.getTabView(i));
            }
        }
        binding.viewPager.setCurrentItem(0);
        onTabSelectView(binding.tabLayout.getTabAt(0));
    }

    private void initTabLayout(){

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabSelectView(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView textView=view.findViewById(R.id.tv_title);
                textView.setTextColor(Res.getColor(context, R.color.color_999999));
                ImageView imgIcon  = view.findViewById(R.id.iv_icon);
                imgIcon.setImageResource(mIncomeMenuInfoList.get(tab.getPosition()).iconUnSelect);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
            }
        });
    }

    private void onTabSelectView(TabLayout.Tab tab){
        IncomeMenuInfo incomeMenuInfo = mIncomeMenuInfoList.get(tab.getPosition());
        View view = tab.getCustomView();
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setTextColor(Res.getColor(context, R.color.color_333333));
        ImageView imgIcon  = view.findViewById(R.id.iv_icon);
        imgIcon.setImageResource(incomeMenuInfo.iconSelect);

        binding.titleBar.setTitle(incomeMenuInfo.tilte);
    }
}