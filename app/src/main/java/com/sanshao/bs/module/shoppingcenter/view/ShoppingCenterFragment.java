package com.sanshao.bs.module.shoppingcenter.view;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ShoppingCenterFragmentBinding;
import com.sanshao.bs.module.shoppingcenter.bean.ShoppingCenterResponse;
import com.sanshao.bs.module.shoppingcenter.model.IShoppingCenterModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GoodsTypeAdapter;
import com.sanshao.bs.module.shoppingcenter.view.adapter.ServiceTypeAdapter;
import com.sanshao.bs.module.shoppingcenter.viewmodel.ShoppingCenterViewModel;

/**
 * 商城
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class ShoppingCenterFragment extends BaseFragment<ShoppingCenterViewModel, ShoppingCenterFragmentBinding> implements IShoppingCenterModel {

    private ServiceTypeAdapter mServiceTypeAdapter;
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private ShoppingCenterViewModel mShoppingCenterViewModel;

    public static ShoppingCenterFragment newInstance() {
        return new ShoppingCenterFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.shopping_center_fragment;
    }

    @Override
    public void initData() {

        mShoppingCenterViewModel = new ShoppingCenterViewModel();
        mServiceTypeAdapter = new ServiceTypeAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        binding.serviceTypeRecyclerView.setLayoutManager(gridLayoutManager);
        binding.serviceTypeRecyclerView.setAdapter(mServiceTypeAdapter);
        binding.serviceTypeRecyclerView.setNestedScrollingEnabled(false);
        mServiceTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsListActivity.start(getContext());
            }
        });

        mGoodsTypeAdapter = new GoodsTypeAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.goodsTypeRecyclerView.setNestedScrollingEnabled(false);
        binding.goodsTypeRecyclerView.setLayoutManager(linearLayoutManager);
        binding.goodsTypeRecyclerView.setAdapter(mGoodsTypeAdapter);
        mGoodsTypeAdapter.setOnItemClickListener((adapter, view, position) -> GoodsListActivity.start(getContext()));
        mShoppingCenterViewModel.getGoodsList(this);
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
        if (shoppingCenterResponse == null){
            return;
        }
        binding.homeBannerLayout.setData(shoppingCenterResponse.banner);
        Glide.with(getContext())
                .load(shoppingCenterResponse.ad)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivAd);
        mGoodsTypeAdapter.addData(shoppingCenterResponse.goodsTypeInfoList);
    }
}