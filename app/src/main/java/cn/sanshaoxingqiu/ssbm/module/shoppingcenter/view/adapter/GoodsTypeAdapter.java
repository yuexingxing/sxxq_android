package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsTypeInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.GoodsDetailActivity;
import cn.sanshaoxingqiu.ssbm.util.GlideUtil;

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
        RecyclerView recyclerView = helper.getView(R.id.goods_type_detail_recycler_view);

        if (TextUtils.isEmpty(item.artitag_url)) {
            helper.getView(R.id.iv_bg).setVisibility(View.INVISIBLE);
            helper.setText(R.id.tv_title, item.artitag_name);
            helper.getView(R.id.ll_title_top).setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(SSApplication.app, 2);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);

            GoodsTypeDetailVerticalAdapter goodsTypeDetailVerticalAdapter = new GoodsTypeDetailVerticalAdapter();
            recyclerView.setAdapter(goodsTypeDetailVerticalAdapter);
            goodsTypeDetailVerticalAdapter.addData(item.set_meal_product);
            goodsTypeDetailVerticalAdapter.setOnItemClickListener((adapter, view, position) -> {
                GoodsDetailInfo goodsDetailInfo = goodsTypeDetailVerticalAdapter.getData().get(position);
                GoodsDetailActivity.start(view.getContext(), goodsDetailInfo.sarti_id);
            });
        } else {
            helper.getView(R.id.iv_bg).setVisibility(View.VISIBLE);
            GlideUtil.loadImageWithNoPlaceHolder(item.artitag_url, helper.getView(R.id.iv_bg));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SSApplication.app);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            helper.getView(R.id.ll_title_top).setVisibility(View.INVISIBLE);
            recyclerView.setLayoutManager(linearLayoutManager);

            GoodsTypeDetailHorizontalAdapter goodsTypeDetailHorizontalAdapter = new GoodsTypeDetailHorizontalAdapter();
            recyclerView.setAdapter(goodsTypeDetailHorizontalAdapter);
            goodsTypeDetailHorizontalAdapter.addData(item.set_meal_product);
            goodsTypeDetailHorizontalAdapter.setOnItemClickListener((adapter, view, position) -> {
                GoodsDetailInfo goodsDetailInfo = goodsTypeDetailHorizontalAdapter.getData().get(position);
                GoodsDetailActivity.start(view.getContext(), goodsDetailInfo.sarti_id);
            });
        }
    }
}
