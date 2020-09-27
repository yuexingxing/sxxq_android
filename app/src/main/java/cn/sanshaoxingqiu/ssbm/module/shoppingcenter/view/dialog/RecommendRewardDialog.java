package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.Res;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.home.model.BannerInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.RecommendRewardAdapter;

/**
 * 商品推荐弹窗
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class RecommendRewardDialog {

    public void show(Context context, GoodsDetailInfo goodsDetailInfo) {
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_recommend_reward, null);
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        TextView tvTitle = layout.findViewById(R.id.tv_title);
        TextView tvContent = layout.findViewById(R.id.tv_content);
        dialog.setContentView(layout);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        layout.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (goodsDetailInfo.isFree()) {
            tvTitle.setText("会员专享");
            tvContent.setText(Res.getString(context, R.string.register_tip));
            return;
        } else if (goodsDetailInfo.isPayByPoint()) {
            tvTitle.setText("邀请有礼，一起变美");
            tvContent.setText(Res.getString(context, R.string.goods_detail_invite_tip3));
            return;
        }

        if (!ContainerUtil.isEmpty(goodsDetailInfo.mem_commission_config)) {
            RecommendRewardAdapter recommendRewardAdapter = new RecommendRewardAdapter();
            RecyclerView recyclerView = layout.findViewById(R.id.recycler_view);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recommendRewardAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            recommendRewardAdapter.addData(goodsDetailInfo.mem_commission_config);
        }
    }
}
