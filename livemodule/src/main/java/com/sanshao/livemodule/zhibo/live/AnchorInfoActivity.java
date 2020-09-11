package com.sanshao.livemodule.zhibo.live;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.widget.TabFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.ActivityAnchorInfoBinding;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;
import com.smarx.notchlib.NotchScreenManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;
import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE;
import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING;

/**
 * 主播主页
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class AnchorInfoActivity extends BaseActivity<BaseViewModel, ActivityAnchorInfoBinding> {

    private List<Fragment> mFragmentList;
    private TabFragmentPagerAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, AnchorInfoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anchor_info;
    }

    @Override
    public void initData() {

        initTabLayout();
        initViewPager();
//        NotchScreenManager.getInstance().setDisplayInNotch(this);
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
        binding.tvStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartLiveActivity.start(context);
            }
        });

        TCUserMgr tcUserMgr = TCUserMgr.getInstance();
        binding.tvName.setText(tcUserMgr.getNickname());
        GlideUtil.loadImage(tcUserMgr.getAvatar(), binding.ivAvatar);
    }

    private void initTabLayout() {

        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("作品集"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("喜欢"));

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
        mFragmentList.add(AnchorWorksFragment.newInstance());
        mFragmentList.add(AnchorWorksFragment.newInstance());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(0);
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