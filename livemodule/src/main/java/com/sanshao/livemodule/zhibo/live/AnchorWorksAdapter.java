package com.sanshao.livemodule.zhibo.live;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.GlideUtil;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;


/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class AnchorWorksAdapter extends BaseQuickAdapter<TCVideoInfo, BaseViewHolder> {

    public AnchorWorksAdapter() {
        super(R.layout.item_layout_anchor_works, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TCVideoInfo item) {
        helper.setText(R.id.tv_title, item.title);
        GlideUtil.loadImage(item.frontCover, (ImageView) helper.getView(R.id.iv_icon));
    }
}
