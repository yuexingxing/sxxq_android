package cn.sanshaoxingqiu.ssbm.module.order.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.util.ContainerUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.ConfirmOrderAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

/**
 * 多套餐
 *
 * @Author yuexingxing
 * @time 2020/7/16
 */
public class MulitySetMealView extends LinearLayout {

    public ConfirmOrderAdapter mConfirmOrderAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTvOrderState;
    private LinearLayout mLlTitleBg;
    private int mOptType;

    public void setOnItemClickListener(ConfirmOrderAdapter.OnItemClickListener listener) {
        mConfirmOrderAdapter.setOnItemClickListener(listener);
    }

    public MulitySetMealView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_mulity_set_meal, this);
        initView(context);
    }

    public void initView(Context context) {

        mLlTitleBg = findViewById(R.id.ll_title_bg);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTvOrderState = findViewById(R.id.tv_order_state);
        mConfirmOrderAdapter = new ConfirmOrderAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mConfirmOrderAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mConfirmOrderAdapter.setFragmentManager(fragmentManager);
    }

    public void setOptType(int optType) {
        mOptType = optType;
        mConfirmOrderAdapter.setOptType(optType);

        LayoutParams layoutParams = (LayoutParams) mLlTitleBg.getLayoutParams();
        if (mOptType == ConfirmOrderAdapter.OPT_TYPE_CONFIRM_ORDER) {
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
            mLlTitleBg.setLayoutParams(layoutParams);
        } else {

        }
    }

    public void setData(List<GoodsDetailInfo> goodsDetailInfoList) {
        if (ContainerUtil.isEmpty(goodsDetailInfoList)) {
            return;
        }
        GoodsDetailInfo goodsDetailInfo = goodsDetailInfoList.get(0);
        mTvOrderState.setText(OrderInfo.getOrderStatus(goodsDetailInfo.sale_status));
        mConfirmOrderAdapter.addData(goodsDetailInfoList);
    }

    public List<GoodsDetailInfo> getData() {
        return mConfirmOrderAdapter.getData();
    }

    public void setTitleVisible(int visible) {
        mLlTitleBg.setVisibility(visible);
    }
}
