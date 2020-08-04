package com.sanshao.bs.module.shoppingcenter.view;

import android.text.InputFilter;
import android.util.Log;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ShoppingCenterFragmentBinding;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsTypeInfo;
import com.sanshao.bs.module.shoppingcenter.bean.ShoppingCenterResponse;
import com.sanshao.bs.module.shoppingcenter.model.IShoppingCenterModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GoodsTypeAdapter;
import com.sanshao.bs.module.shoppingcenter.view.adapter.ServiceTypeAdapter;
import com.sanshao.bs.module.shoppingcenter.viewmodel.ShoppingCenterViewModel;

/**
 * 商城
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class ShoppingCenterFragment extends BaseFragment<ShoppingCenterViewModel, ShoppingCenterFragmentBinding> implements IShoppingCenterModel {

    private GoodsTypeAdapter mGoodsTypeAdapter;

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
        mGoodsTypeAdapter.setOnItemClickListener((adapter, view, position) -> GoodsListActivity.start(getContext()));

        binding.nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d(TAG, "dy-" + scrollY + "/" + oldScrollY);
                if (scrollY > ScreenUtil.dp2px(context, 200)) {
                    binding.ivToTop.setVisibility(View.VISIBLE);
                } else {
                    binding.ivToTop.setVisibility(View.GONE);
                }
            }
        });

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> mViewModel.getGoodsList());

        binding.ivToTop.setOnClickListener(view -> {
            binding.nestedScrollview.smoothScrollTo(0, 0);
        });
        mViewModel.getGoodsList();
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
    public void returnShoppingCenterList(ShoppingCenterResponse shoppingCenterResponse) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (shoppingCenterResponse == null) {
            return;
        }
        binding.homeBannerLayout.setData(shoppingCenterResponse.slideshow);
        if (!ContainerUtil.isEmpty(shoppingCenterResponse.static_advertising)) {
            Glide.with(getContext())
                    .load(shoppingCenterResponse.static_advertising.get(0).artitag_url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.ivAd);
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
}