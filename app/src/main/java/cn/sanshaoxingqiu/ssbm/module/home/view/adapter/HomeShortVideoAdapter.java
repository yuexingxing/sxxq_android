package cn.sanshaoxingqiu.ssbm.module.home.view.adapter;

import androidx.fragment.app.FragmentManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.CommonCallBack;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.shortvideo.ShortVideoFragment;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * 短视频
 *
 * @Author yuexingxing
 * @time 2020/9/16
 */
public class HomeShortVideoAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {
    private CommonCallBack mCommonCallBack;
    private FragmentManager mFragmentManager;

    public HomeShortVideoAdapter(List<VideoInfo> data, FragmentManager fragmentManager) {
        super(R.layout.item_layout_home_short_video, data);
        this.mFragmentManager = fragmentManager;
    }

    public void setCommonCallBack(CommonCallBack commonCallBack) {
        mCommonCallBack = commonCallBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {

        if (mFragmentManager != null){
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, ShortVideoFragment.newInstance())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                    .commit();
        }
    }
}
