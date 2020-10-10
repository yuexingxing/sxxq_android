package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ShoppingCenterFragmentBinding;
import cn.sanshaoxingqiu.ssbm.module.home.model.BannerInfo;
import cn.sanshaoxingqiu.ssbm.module.invitation.view.InvitationActivity;
import cn.sanshaoxingqiu.ssbm.module.register.view.RegisterActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsTypeInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.ShoppingCenterResponse;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IShoppingCenterModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.util.ShoppingCenterUtil;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.AdAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.GoodsTypeAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel.ShoppingCenterViewModel;

/**
 * 商城
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class ShoppingCenterFragment extends BaseFragment<ShoppingCenterViewModel, ShoppingCenterFragmentBinding> implements IShoppingCenterModel {

    private GoodsTypeAdapter mGoodsTypeAdapter;
    private AdAdapter mAdAdapter;
    private BannerInfo mAdBannerInfo;

    public static ShoppingCenterFragment newInstance() {
        return new ShoppingCenterFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.shopping_center_fragment;
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
        mGoodsTypeAdapter = new GoodsTypeAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.goodsTypeRecyclerView.setNestedScrollingEnabled(false);
        binding.goodsTypeRecyclerView.setLayoutManager(linearLayoutManager);
        binding.goodsTypeRecyclerView.setAdapter(mGoodsTypeAdapter);
        mGoodsTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsListActivity.start(context, mGoodsTypeAdapter.getData().get(position));
        });

        mAdAdapter = new AdAdapter();
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        binding.activitysRecyclerView.setNestedScrollingEnabled(false);
        binding.activitysRecyclerView.setLayoutManager(linearLayoutManager2);
        binding.activitysRecyclerView.setAdapter(mAdAdapter);
        mAdAdapter.setOnItemClickListener((adapter, view, position) -> {
            jumpBanner(mAdAdapter.getData().get(position));
        });

        binding.homeBannerLayout.setOnBannerClick(bannerInfo -> {
            jumpBanner(bannerInfo);
        });

        binding.emptyLayout.setOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getGoodsList(context);
            }
        });
        binding.nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d(TAG, "dy-" + scrollY + "/" + oldScrollY);
                if (scrollY > ScreenUtil.dp2px(context, 400)) {
                    binding.ivToTop.setVisibility(View.VISIBLE);
                } else {
                    binding.ivToTop.setVisibility(View.GONE);
                }
            }
        });

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> mViewModel.getGoodsList(context));

        binding.ivToTop.setOnClickListener(view -> {
            binding.nestedScrollview.smoothScrollTo(0, 0);
        });
        binding.emptyLayout.showEmpty("", 0);
        mViewModel.getGoodsList(context);
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.transparent;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void returnShoppingCenterList(ShoppingCenterResponse shoppingCenterResponse) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (shoppingCenterResponse == null) {
            binding.emptyLayout.showError();
            return;
        }
        binding.emptyLayout.showSuccess();
        binding.homeBannerLayout.setData(shoppingCenterResponse.slideshow);

        mAdAdapter.getData().clear();
        if (!ContainerUtil.isEmpty(shoppingCenterResponse.static_advertising)) {
            mAdBannerInfo = shoppingCenterResponse.static_advertising.get(0);
            mAdAdapter.addData(shoppingCenterResponse.static_advertising);
        }

        if (!ContainerUtil.isEmpty(shoppingCenterResponse.classify)) {
            for (int i = 0; i < shoppingCenterResponse.classify.size(); i++) {
                GoodsTypeInfo goodsTypeInfo = shoppingCenterResponse.classify.get(i);
                if (ContainerUtil.isEmpty(goodsTypeInfo.set_meal_product)) {
                    shoppingCenterResponse.classify.remove(i);
                    --i;
                }
            }
            mGoodsTypeAdapter.setNewData(shoppingCenterResponse.classify);
        }
        binding.llBottomLine.setVisibility(View.VISIBLE);
    }

    /**
     * 轮播/广告点击跳转
     *
     * @param bannerInfo
     */
    private void jumpBanner(BannerInfo bannerInfo) {

        if (bannerInfo == null) {
            return;
        }
        if (TextUtils.equals(bannerInfo.action_type, BannerInfo.ActionType.GOODS)) {
            if (bannerInfo.action_args != null) {
                GoodsDetailActivity.start(context, bannerInfo.action_args.sarti_id);
            }
        } else if (TextUtils.equals(bannerInfo.action_type, BannerInfo.ActionType.GOODS_LIST)) {
            if (bannerInfo.action_args != null) {
                GoodsTypeInfo goodsTypeInfo = new GoodsTypeInfo();
                goodsTypeInfo.artitag_id = bannerInfo.action_args.artitag_id;
                goodsTypeInfo.artitag_name = bannerInfo.action_args.artitag_name;
                GoodsListActivity.start(context, goodsTypeInfo);
            }
        } else if (TextUtils.equals(bannerInfo.action_type, BannerInfo.ActionType.NO_ACTION)) {

        } else {
            if (bannerInfo.action_args != null) {
                ExerciseActivity.start(context, bannerInfo.artitag_name, bannerInfo.action_args.activity_url);
            }
        }
    }
}