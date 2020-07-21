package com.sanshao.bs.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanshao.bs.R;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.view.GoodsDetailActivity;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GuessYouLoveAdapter;

/**
 * 猜你喜欢封装自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class GuessYouLoveView extends LinearLayout {

    private GuessYouLoveAdapter mGuessYouLoveAdapter;
    private RecyclerView mRecyclerView;

    public GuessYouLoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_guess_you_love, this);
        initView(context);
        initData();
    }

    private void initView(Context context) {
        mRecyclerView = findViewById(R.id.recycler_view);
        mGuessYouLoveAdapter = new GuessYouLoveAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mGuessYouLoveAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
//        mRecyclerView.addItemDecoration(new GuessYouLoveSpaceItemDecoration(ScreenUtil.dp2px(context, 10), 2));
        mGuessYouLoveAdapter.setOnItemClickListener((adapter, view, position) -> GoodsDetailActivity.start(context));
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
            goodsDetailInfo.name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            goodsDetailInfo.price = 200;
            goodsDetailInfo.oldPrice = 240;
            mGuessYouLoveAdapter.addData(goodsDetailInfo);
        }
    }
}
