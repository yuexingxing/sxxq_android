package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.Res;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

/**
 * @Author yuexingxing
 * @time 2020/6/16
 */
public class RecommendRewardAdapter extends BaseQuickAdapter<GoodsDetailInfo.MemberCommissionInfo, BaseViewHolder> {

    public RecommendRewardAdapter() {
        super(R.layout.item_layout_recommend_reward, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo.MemberCommissionInfo item) {
        String str = item.getMember() + "推荐新用户购买此产品可获得";
        SpannableString spannableString = new SpannableString(str + item.amt + "元");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Res.getColor(helper.itemView.getContext(), R.color.color_c52d2d));
        spannableString.setSpan(foregroundColorSpan, str.length(), str.length() + item.amt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.5f);
        spannableString.setSpan(relativeSizeSpan, str.length(), str.length() + item.amt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.tv_title, spannableString);
    }
}
