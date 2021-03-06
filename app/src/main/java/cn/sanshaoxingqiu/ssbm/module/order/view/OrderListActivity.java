package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.exam.commonbiz.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityOrderListBinding;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.TabFragmentPagerAdapter;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderListViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;
import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE;
import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING;

/**
 * 订单列表
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class OrderListActivity extends BaseActivity<OrderListViewModel, ActivityOrderListBinding> {
    private List<Fragment> mFragmentList;
    private TabFragmentPagerAdapter adapter;
    private int orderState;

    public static void start(Context context, int orderState) {
        Intent starter = new Intent(context, OrderListActivity.class);
        starter.putExtra(OrderInfo.ORDER_STATE, orderState);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initData() {
        orderState = getIntent().getIntExtra(OrderInfo.ORDER_STATE, OrderInfo.State.ALL);
        initTabLayout();
        initViewPager();
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

    private void initTabLayout() {

        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.color_999999), ContextCompat.getColor(this, R.color.color_333333));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.color_b6a578));

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("全部"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("待支付"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("待使用"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("已完成"));

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
        mFragmentList.add(OrderStatusFragment.newInstance(OrderInfo.State.ALL));
        mFragmentList.add(OrderStatusFragment.newInstance(OrderInfo.State.ToBePaid));
        mFragmentList.add(OrderStatusFragment.newInstance(OrderInfo.State.ToBeUse));
        mFragmentList.add(OrderStatusFragment.newInstance(OrderInfo.State.Complete));
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(orderState);
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

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayStatusChangedEvent(PayStatusChangedEvent payStatusChangedEvent) {
        if (payStatusChangedEvent == null){
            return;
        }
        if (payStatusChangedEvent.paySuccess){
            finish();
        }
    }
}