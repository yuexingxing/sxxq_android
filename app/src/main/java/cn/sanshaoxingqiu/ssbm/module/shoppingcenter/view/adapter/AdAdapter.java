package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.GlideUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.home.model.BannerInfo;

/**
 * @Author yuexingxing
 * @time 2020/6/16
 */
public class AdAdapter extends BaseQuickAdapter<BannerInfo, BaseViewHolder> {

    public AdAdapter() {
        super(R.layout.item_layout_ad, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BannerInfo item) {
        GlideUtil.loadImage(item.artitag_url, helper.getView(R.id.iv_icon));
    }
}
