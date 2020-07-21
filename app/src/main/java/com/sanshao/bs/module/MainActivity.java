package com.sanshao.bs.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Res;
import com.google.android.material.tabs.TabLayout;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityMainBinding;
import com.sanshao.bs.module.personal.bean.IncomeMenuInfo;
import com.sanshao.bs.module.personal.view.IncomeTabFragmentAdapter;
import com.sanshao.bs.module.personal.view.PersonalFragment;
import com.sanshao.bs.module.shoppingcenter.view.ShoppingCenterFragment;
import com.sanshao.bs.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    private List<Fragment> mFragmentList;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private List<IncomeMenuInfo> mIncomeMenuInfoList;
    private IncomeTabFragmentAdapter mIncomeTabFragmentAdapter;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

        initViewPager();
        initTabLayout();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showShortToast("再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    private void initViewPager() {

        mIncomeMenuInfoList = new ArrayList<>();
        IncomeMenuInfo incomeMenuInfoSort = new IncomeMenuInfo();
        incomeMenuInfoSort.tilte = "商城";
        incomeMenuInfoSort.iconSelect = R.drawable.tab_home_selected;
        incomeMenuInfoSort.iconUnSelect = R.drawable.tab_home_normal;

        IncomeMenuInfo incomeMenuInfo = new IncomeMenuInfo();
        incomeMenuInfo.tilte = "我的";
        incomeMenuInfo.iconSelect = R.drawable.tab_my_selected;
        incomeMenuInfo.iconUnSelect = R.drawable.tab_my_normal;

        mIncomeMenuInfoList.add(incomeMenuInfoSort);
        mIncomeMenuInfoList.add(incomeMenuInfo);

        //把Fragment添加到List集合里面
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ShoppingCenterFragment.newInstance());
        mFragmentList.add(PersonalFragment.newInstance());
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
    }
}