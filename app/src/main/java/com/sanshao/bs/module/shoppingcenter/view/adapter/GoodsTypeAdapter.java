package com.sanshao.bs.module.shoppingcenter.view.adapter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsTypeInfo;
import com.sanshao.bs.module.shoppingcenter.view.GoodsListActivity;

/**
 * @Author yuexingxing
 * @time 2020/6/16
 */
public class GoodsTypeAdapter extends BaseQuickAdapter<GoodsTypeInfo, BaseViewHolder> {

    public GoodsTypeAdapter() {
        super(R.layout.item_layout_goods_type, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsTypeInfo item) {
        helper.setText(R.id.tv_title, item.artitag_name);

        if (!ContainerUtil.isEmpty(item.set_meal_product)){
            GoodsTypeDetailAdapter goodsTypeDetailAdapter = new GoodsTypeDetailAdapter();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SSApplication.app);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            RecyclerView recyclerView = helper.getView(R.id.goods_type_detail_recycler_view);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(goodsTypeDetailAdapter);
            goodsTypeDetailAdapter.addData(item.set_meal_product);
            goodsTypeDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    GoodsListActivity.start(view.getContext());
                }
            });
        }
    }
}
