package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.util.AppManager;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.zhibo.TCGlobalConfig;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;
import com.umeng.analytics.AnalyticsConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.HomeFragmentBinding;
import cn.sanshaoxingqiu.ssbm.module.home.view.adapter.LiveTabFragmentAdapter;
import cn.sanshaoxingqiu.ssbm.module.login.event.LoginEvent;

/**
 * 首页
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class HomeFragment extends BaseFragment<BaseViewModel, HomeFragmentBinding> {
    private final String TAG = HomeFragment.class.getSimpleName();
    private List<Fragment> mFragmentList;
    private LiveTabFragmentAdapter mLiveTabFragmentAdapter;
    private String[] mTitleList = new String[2];
    private VideoBackListFragment mVideoBackListFragment;
    private LiveListFragment mLiveListFragment;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return false;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.transparent;
    }

    @Override
    protected void loadData() {
        if (MLVBLiveRoomImpl.mInstance != null && !MLVBLiveRoomImpl.mInstance.isLoginLiveRoom()) {
            TCUserMgr.getInstance().loginMLVB();
        }
        if (SSApplication.isLogin()) {
            if (TCGlobalConfig.isUserSignEmpty()) {
                TCGlobalConfig.getUserSign();
            }
        }
    }

    @Override
    public void initData() {

        MLVBLiveRoomImpl.sharedInstance(SSApplication.getInstance());
        MLVBLiveRoomImpl.mInstance.initHttpRequest();
        initViewPager();
        initTabLayout();
    }

    @Override
    public void onPause() {
        super.onPause();
        onInVisible();
    }

    @Override
    protected void onVisible() {

    }

    @Override
    protected void onInVisible() {
        if (mLiveListFragment != null) {
            mLiveListFragment.onInVisible();
        }
        if (mVideoBackListFragment != null) {
            mVideoBackListFragment.onInVisible();
        }
    }

    private void initTabLayout() {

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Log.i(TAG, "======我选中了====");
                onTabSelectView(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
                Log.i(TAG, "======我未被选中====");
                onTabUnSelectView(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
                if (tab.getPosition() == 0 && mVideoBackListFragment != null) {
                    mVideoBackListFragment.scrollToTop();
                } else if (tab.getPosition() == 1 && mLiveListFragment != null) {
                    mLiveListFragment.scrollToTop();
                }
            }
        });
    }

    private void initViewPager() {

        mTitleList[0] = "推荐";
        mTitleList[1] = "直播";
        mVideoBackListFragment = VideoBackListFragment.newInstance();
        mLiveListFragment = LiveListFragment.newInstance();

        //把Fragment添加到List集合里面
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mVideoBackListFragment);
        mFragmentList.add(mLiveListFragment);

        mLiveTabFragmentAdapter = new LiveTabFragmentAdapter(mFragmentList, mTitleList, getChildFragmentManager(), context);
        binding.viewPager.setAdapter(mLiveTabFragmentAdapter);
        binding.viewPager.setOffscreenPageLimit(mFragmentList.size());
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mLiveTabFragmentAdapter.getTabView(i));
                onTabUnSelectView(tab);
            }
        }

        binding.viewPager.setCurrentItem(0);
        onTabSelectView(binding.tabLayout.getTabAt(0));
    }

    private void onTabSelectView(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setTextColor(Res.getColor(context, R.color.white));
        textView.setTextSize(17f);
    }

    private void onTabUnSelectView(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setTextColor(Res.getColor(context, R.color.white));
        textView.setTextSize(15f);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent loginEvent) {
        if (loginEvent == null) {
            return;
        }
        TCUserMgr.getInstance().loginMLVB();
    }
}