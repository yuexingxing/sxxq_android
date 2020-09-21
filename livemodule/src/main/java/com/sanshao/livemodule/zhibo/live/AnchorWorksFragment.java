package com.sanshao.livemodule.zhibo.live;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.FragmentLayoutAnchorWorksBinding;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

/**
 * 主播作品
 *
 * @Author yuexingxing
 * @time 2020/9/10
 */
public class AnchorWorksFragment extends BaseFragment<LiveViewModel, FragmentLayoutAnchorWorksBinding> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, ILiveRoomModel {

    private AnchorWorksAdapter mAnchorWorksAdapter;

    public static AnchorWorksFragment newInstance() {
        AnchorWorksFragment fragment = new AnchorWorksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_anchor_works;
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void initData() {

        mViewModel.setILiveRoomModel(this);
        mAnchorWorksAdapter = new AnchorWorksAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(mAnchorWorksAdapter);
        binding.recyclerView.setNestedScrollingEnabled(true);
        binding.recyclerView.setHasFixedSize(true);

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.emptyLayout.setOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });

        String picUrl = "http://img.cyw.com/shopx/20130606155913125664/shopinfo/201605041441522.jpg";
        for (int i = 0; i < 10; i++) {
            TCVideoInfo tcVideoInfo = new TCVideoInfo();
            tcVideoInfo.title = i + "大佛家里房间里看书的风景阿迪是否了解";
            tcVideoInfo.frontCover = picUrl;
            mAnchorWorksAdapter.addData(tcVideoInfo);
        }
    }

    @Override
    public void onRefresh() {
        mViewModel.getLiveVideoList();
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void returnUserSign(UserSignResponse userSignResponse) {

    }

    @Override
    public void returnGetVideoList(VideoListResponse videoListResponse) {

    }
}