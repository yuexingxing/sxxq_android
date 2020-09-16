package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

import cn.sanshaoxingqiu.ssbm.R;
import com.exam.commonbiz.util.GlideUtil;

public class HomeAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {

    private CommonCallBack mCommonCallBack;

    HomeAdapter() {
        super(R.layout.item_layout_home, null);
    }

    public void setCommonCallBack(CommonCallBack commonCallBack) {
        mCommonCallBack = commonCallBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {

        helper.setText(R.id.tv_title, item.live_title);
//        helper.setText(R.id.tv_content, item.nickname);
        GlideUtil.loadImage(item.frontcover, helper.getView(R.id.iv_bg), R.drawable.icon_goods_type_six);

        FrameLayout frameLayout = helper.getView(R.id.fl_content);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenHeight(helper.itemView.getContext());
        frameLayout.setLayoutParams(layoutParams);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCommonCallBack != null) {
                    mCommonCallBack.callback(helper.getAdapterPosition(), item);
                }
            }
        });
    }
}
