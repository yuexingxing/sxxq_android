package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.util.ContainerUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGuessYouLoveModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.GoodsDetailActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.GuessYouLoveAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel.GuessYouLoveViewModel;

import java.util.List;

/**
 * 猜你喜欢封装自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class GuessYouLoveView extends LinearLayout implements IGuessYouLoveModel {

    private GuessYouLoveViewModel mGuessYouLoveViewModel;
    private GuessYouLoveAdapter mGuessYouLoveAdapter;
    private RecyclerView mRecyclerView;

    public GuessYouLoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_guess_you_love, this);
        initView(context);
    }

    private void initView(Context context) {

        mGuessYouLoveViewModel = new GuessYouLoveViewModel();
        mGuessYouLoveViewModel.setCallBack(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mGuessYouLoveAdapter = new GuessYouLoveAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mGuessYouLoveAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        mGuessYouLoveAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsDetailInfo goodsDetailInfo = mGuessYouLoveAdapter.getData().get(position);
            GoodsDetailActivity.start(context, goodsDetailInfo.sarti_id);
        });
    }

    public void getData() {
        mGuessYouLoveViewModel.getGuessYouLoveData();
    }

    @Override
    public void returnGuessYouLoveData(List<GoodsDetailInfo> goodsDetailInfoList) {
        if (ContainerUtil.isEmpty(goodsDetailInfoList)) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        mGuessYouLoveAdapter.addData(goodsDetailInfoList);
    }
}
