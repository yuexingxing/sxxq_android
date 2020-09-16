package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.content.Intent;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.IBaseModel;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.playback.TCPlaybackActivity;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentLayoutVideoBackListBinding;
import cn.sanshaoxingqiu.ssbm.util.Constants;

/**
 * 首页-回放列表
 *
 * @Author yuexingxing
 * @time 2020/9/16
 */
public class VideoBackListFragment extends BaseFragment<LiveViewModel, FragmentLayoutVideoBackListBinding> implements IBaseModel, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int START_LIVE_PLAY = 100;
    private HomeAdapter mHomeAdapter;
    private int mPageNum = 1;

    public static VideoBackListFragment newInstance() {
        VideoBackListFragment fragment = new VideoBackListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_video_back_list;
    }

    @Override
    public void initData() {

        mViewModel.setIBaseModel(this);
        mHomeAdapter = new HomeAdapter();
        binding.emptyLayout.showSuccess();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.setCommonCallBack(new CommonCallBack() {
            @Override
            public void callback(int postion, Object object) {
                startLivePlay(mHomeAdapter.getItem(postion));
            }
        });

        mHomeAdapter.setOnLoadMoreListener(this, binding.recyclerView);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            mPageNum = 1;
            getLiveData();
        });

        getLiveData();
    }

    private void getLiveData() {
        mViewModel.getVideoBackList(mPageNum, Constants.PAGE_SIZE);
    }

    /**
     * 开始播放视频
     *
     * @param item 视频数据
     */
    private void startLivePlay(final VideoInfo item) {

        Intent intent = new Intent(getActivity(), TCPlaybackActivity.class);
        intent.putExtra(TCConstants.PLAY_URL, item.rtmp_pull_url);
        intent.putExtra(TCConstants.PUSHER_ID, item.pushers.invitation_code);
        intent.putExtra(TCConstants.PUSHER_NAME, item.pushers.anchor_name);
        intent.putExtra(TCConstants.PUSHER_AVATAR, item.pushers.anchor_name);
        intent.putExtra(TCConstants.HEART_COUNT, TextUtils.isEmpty(item.like_number) ? "0" : item.like_number);
        intent.putExtra(TCConstants.MEMBER_COUNT, item.viewer_count);
        intent.putExtra(TCConstants.GROUP_ID, item.room_id);
        intent.putExtra(TCConstants.PLAY_TYPE, item.meta_type);
        intent.putExtra(TCConstants.FILE_ID, "");
        intent.putExtra(TCConstants.COVER_PIC, item.frontcover);
        intent.putExtra(TCConstants.TIMESTAMP, item.live_start_time);
        intent.putExtra(TCConstants.ROOM_TITLE, item.live_title);
        startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onLoadMoreRequested() {
        mPageNum++;
        getLiveData();
    }

    @Override
    public void onRefreshData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            return;
        }

        VideoListResponse videoListResponse = (VideoListResponse) object;
        if (ContainerUtil.isEmpty(videoListResponse.rows)) {
            binding.emptyLayout.showEmpty("暂无直播", R.drawable.image_nolive);
            return;
        }

        mHomeAdapter.setNewData(videoListResponse.rows);
        mHomeAdapter.loadMoreComplete();
        binding.emptyLayout.showSuccess();
    }

    @Override
    public void onLoadMoreData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            return;
        }

        VideoListResponse videoListResponse = (VideoListResponse) object;
        if (ContainerUtil.isEmpty(videoListResponse.rows)) {
            mHomeAdapter.loadMoreEnd();
            return;
        }

        mHomeAdapter.addData(videoListResponse.rows);
        mHomeAdapter.loadMoreComplete();
        binding.emptyLayout.showSuccess();
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetError() {

    }
}
