package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.IBaseModel;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentLayoutHomeShortVideoBinding;
import cn.sanshaoxingqiu.ssbm.module.home.view.adapter.HomeShortVideoAdapter;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;

/**
 * 首页-短视频
 *
 * @Author yuexingxing
 * @time 2020/10/26
 */
public class HomeShortVideoFragment extends BaseFragment<LiveViewModel, FragmentLayoutHomeShortVideoBinding> implements IBaseModel, BaseQuickAdapter.RequestLoadMoreListener{

    private HomeShortVideoAdapter mHomeShortVideoAdapter;
    private List<VideoInfo> mVideoListData = new ArrayList<>();

    public static HomeShortVideoFragment newInstance() {
        HomeShortVideoFragment fragment = new HomeShortVideoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_home_short_video;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initData() {

        mViewModel.setIBaseModel(this);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View emptyLayout = inflater.inflate(R.layout.item_layout_empty_live, null);
        emptyLayout.findViewById(R.id.ll_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLiveData();
            }
        });
        mHomeShortVideoAdapter = new HomeShortVideoAdapter(mVideoListData, getChildFragmentManager());
        mHomeShortVideoAdapter.setEmptyView(emptyLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mHomeShortVideoAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerView);
        binding.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {

            }
        });
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        mHomeShortVideoAdapter.setCommonCallBack(new CommonCallBack() {
            @Override
            public void callback(int postion, Object object) {
                if (!SSApplication.isLogin()) {
                    LoginActivity.start(context);
                    return;
                }
                if (MLVBLiveRoomImpl.mInstance.isLoginLiveRoom()) {
                    startPlayVideo();
                } else {
                    TCUserMgr.getInstance().loginMLVB();
                }
            }
        });

        mHomeShortVideoAdapter.setOnLoadMoreListener(this, binding.recyclerView);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            getLiveData();
        });
        getLiveData();
    }

    public void getLiveData() {
//        mViewModel.getLiveVideoList();

        mVideoListData.clear();
        for (int i = 0; i <10 ; i++) {
            VideoInfo videoInfo = new VideoInfo();
            mVideoListData.add(videoInfo);
        }
        mHomeShortVideoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onRefreshData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        mHomeShortVideoAdapter.getData().clear();
        mHomeShortVideoAdapter.notifyDataSetChanged();
        if (object == null) {
            mHomeShortVideoAdapter.isUseEmpty(true);
            return;
        }

        VideoListResponse videoListResponse = (VideoListResponse) object;
        if (ContainerUtil.isEmpty(videoListResponse.rows)) {
            mHomeShortVideoAdapter.isUseEmpty(true);
            return;
        }

        mVideoListData.clear();
        mVideoListData.addAll(videoListResponse.rows);
        mHomeShortVideoAdapter.notifyDataSetChanged();

        mHomeShortVideoAdapter.isUseEmpty(false);
        mHomeShortVideoAdapter.loadMoreComplete();
        mHomeShortVideoAdapter.loadMoreEnd();
    }

    @Override
    public void onLoadMoreData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            return;
        }

        VideoListResponse videoListResponse = (VideoListResponse) object;
        if (ContainerUtil.isEmpty(videoListResponse.rows)) {
            mHomeShortVideoAdapter.loadMoreEnd();
            return;
        }

        mVideoListData.addAll(videoListResponse.rows);
        mHomeShortVideoAdapter.loadMoreComplete();
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetError() {

    }

    /**
     * 开始播放视频
     */
    private void startPlayVideo() {

    }
}
