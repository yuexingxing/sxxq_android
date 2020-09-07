package cn.sanshaoxingqiu.ssbm.module.home.view;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.util.GlideUtil;

public class HomeAdapter extends BaseQuickAdapter<TCVideoInfo, BaseViewHolder> {

    HomeAdapter() {
        super(R.layout.item_layout_home, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TCVideoInfo item) {

        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_content, item.nickname);
        GlideUtil.loadImage(item.frontCover, helper.getView(R.id.iv_bg), R.drawable.icon_goods_type_six);
    }
}
