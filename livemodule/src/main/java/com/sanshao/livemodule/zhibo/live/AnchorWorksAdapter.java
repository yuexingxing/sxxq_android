package com.sanshao.livemodule.zhibo.live;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.ScreenUtil;
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

        FrameLayout flContent = helper.getView(R.id.fl_content);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) flContent.getLayoutParams();

        //正好最后一排两条数据
        if (getData().size() % 2 == 0) {
            if (helper.getAdapterPosition() == getData().size() - 1 || helper.getAdapterPosition() == getData().size() - 2) {
                layoutParams.bottomMargin = ScreenUtil.dp2px(helper.itemView.getContext(), 12);
            }
        }
        //最后一排一条数据
        else {
            if (helper.getAdapterPosition() == getData().size() - 1) {
                layoutParams.bottomMargin = ScreenUtil.dp2px(helper.itemView.getContext(), 12);
            }
        }
        flContent.setLayoutParams(layoutParams);
    }
}
