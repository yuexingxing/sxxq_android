package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.StatusBarUtil;
import com.google.android.material.tabs.TabLayout;
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.zhibo.TCGlobalConfig;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.HomeFragmentBinding;
import cn.sanshaoxingqiu.ssbm.module.home.view.adapter.LiveTabFragmentAdapter;
import cn.sanshaoxingqiu.ssbm.module.login.event.LoginEvent;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;

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
        return R.color.transparent_half;
    }

    @Override
    protected void loadData() {
        if (SSApplication.isLogin()) {
            if (TCGlobalConfig.isUserSignEmpty()) {
                TCGlobalConfig.getUserSign();
            } else {
                TCUserMgr.getInstance().loginMLVB();
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
                Log.i(TAG, "======我再次被选中====");
            }
        });
    }

    private void initViewPager() {

        mTitleList[0] = "直播";
        mTitleList[1] = "推荐";

        //把Fragment添加到List集合里面
        mFragmentList = new ArrayList<>();
        mFragmentList.add(LiveListFragment.newInstance());
        mFragmentList.add(VideoBackListFragment.newInstance());

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
        textView.setTextColor(Res.getColor(context, R.color.color_bbbbbb));
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