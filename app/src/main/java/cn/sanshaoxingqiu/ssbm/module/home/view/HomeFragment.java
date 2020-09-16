package cn.sanshaoxingqiu.ssbm.module.home.view;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.google.android.material.tabs.TabLayout;
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.zhibo.TCGlobalConfig;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.HomeFragmentBinding;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.TabFragmentPagerAdapter;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;
import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE;
import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING;

/**
 * 首页
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class HomeFragment extends BaseFragment<BaseViewModel, HomeFragmentBinding> {

    private List<Fragment> mFragmentList;
    private TabFragmentPagerAdapter adapter;

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
        initTabLayout();
        initViewPager();
    }

    private void initTabLayout() {

        binding.tabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.white), ContextCompat.getColor(getContext(), R.color.white));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.color_b6a578));

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("直播"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("推荐"));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager() {

        //绑定点击事件
        binding.viewPager.setOnPageChangeListener(new TabLayoutOnPageChangeListener(binding.tabLayout));
        //把Fragment添加到List集合里面
        mFragmentList = new ArrayList<>();
        mFragmentList.add(LiveListFragment.newInstance());
        mFragmentList.add(VideoBackListFragment.newInstance());
        adapter = new TabFragmentPagerAdapter(getChildFragmentManager(), mFragmentList);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(mFragmentList.size());
    }

    public class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<TabLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset,
                                   final int positionOffsetPixels) {
            final TabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null) {
                // Only update the text selection if we're not settling, or we are settling after
                // being dragged
                final boolean updateText = mScrollState != SCROLL_STATE_SETTLING ||
                        mPreviousScrollState == SCROLL_STATE_DRAGGING;
                // Update the indicator if we're not settling after being idle. This is caused
                // from a setCurrentItem() call and will be handled by an animation from
                // onPageSelected() instead.
                final boolean updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING
                        && mPreviousScrollState == SCROLL_STATE_IDLE);
                tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
            }
        }

        @Override
        public void onPageSelected(final int position) {
            final TabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position
                    && position < tabLayout.getTabCount()) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                // (since onPageScrolled will handle that).
                final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE
                        || (mScrollState == SCROLL_STATE_SETTLING
                        && mPreviousScrollState == SCROLL_STATE_IDLE);
                tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator);
            }
        }

        void reset() {
            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }
    }
}