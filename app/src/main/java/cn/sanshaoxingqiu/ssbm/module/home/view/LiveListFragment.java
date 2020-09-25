package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
import com.sanshao.livemodule.zhibo.audience.TCAudienceActivity;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentLayoutLiveListBinding;
import cn.sanshaoxingqiu.ssbm.module.home.view.adapter.HomeLiveAdapter;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;

/**
 * 首页-直播列表
 *
 * @Author yuexingxing
 * @time 2020/9/16
 */
public class LiveListFragment extends BaseFragment<LiveViewModel, FragmentLayoutLiveListBinding> implements IBaseModel, BaseQuickAdapter.RequestLoadMoreListener {
    public static final int START_LIVE_PLAY = 100;
    private HomeLiveAdapter mHomeAdapter;
    private TXLivePlayer mCurrentTXLivePlayer;

    public static LiveListFragment newInstance() {
        LiveListFragment fragment = new LiveListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_live_list;
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
        mHomeAdapter = new HomeLiveAdapter(null);
        mHomeAdapter.setEmptyView(emptyLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mHomeAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerView);
        binding.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                enterLiveRoom(view);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                leaveLiveRoom(view);
            }
        });
        mHomeAdapter.setCommonCallBack(new CommonCallBack() {
            @Override
            public void callback(int postion, Object object) {
                if (!SSApplication.isLogin()) {
                    LoginActivity.start(context);
                    return;
                }
                if (MLVBLiveRoomImpl.mInstance.isLoginLiveRoom()) {
                    startLivePlay(mHomeAdapter.getItem(postion));
                } else {
                    TCUserMgr.getInstance().loginMLVB();
                }
            }
        });

        mHomeAdapter.setOnLoadMoreListener(this, binding.recyclerView);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            getLiveData();
        });
        getLiveData();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (mCurrentTXLivePlayer != null) {
            mCurrentTXLivePlayer.resume();
            Log.d(TAG, "LiveListFragment-直播播放了");
        }
    }

    @Override
    protected void onInVisible() {
        if (mCurrentTXLivePlayer != null && mCurrentTXLivePlayer.isPlaying()) {
            mCurrentTXLivePlayer.pause();
            Log.d(TAG, "LiveListFragment-直播暂停播放了");
        }
    }

    private void enterLiveRoom(View view) {
        mCurrentTXLivePlayer = null;
        TXCloudVideoView txCloudVideoView = view.findViewById(R.id.anchor_video_view);
        ImageView ivLiveBg = view.findViewById(R.id.iv_bg);
        if (txCloudVideoView == null || ivLiveBg == null) {
            return;
        }
        VideoInfo videoInfo = (VideoInfo) txCloudVideoView.getTag();
        TXLivePlayer txLivePlayer = (TXLivePlayer) ivLiveBg.getTag();
        if (txLivePlayer != null) {
            txLivePlayer.setPlayerView(txCloudVideoView);
            txLivePlayer.startPlay(videoInfo.flv_pull_url, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
            mCurrentTXLivePlayer = txLivePlayer;
            ivLiveBg.setVisibility(View.GONE);
            Log.d(TAG, "播放成功：" + videoInfo.room_id);
        }
    }

    private void leaveLiveRoom(View view) {
        TXCloudVideoView txCloudVideoView = view.findViewById(R.id.anchor_video_view);
        ImageView ivLiveBg = view.findViewById(R.id.iv_bg);
        if (txCloudVideoView == null || ivLiveBg == null) {
            return;
        }
        VideoInfo videoInfo = (VideoInfo) txCloudVideoView.getTag();
        TXLivePlayer txLivePlayer = (TXLivePlayer) ivLiveBg.getTag();
        if (txLivePlayer != null) {
            txLivePlayer.pause();
            ivLiveBg.setVisibility(View.VISIBLE);
            Log.d(TAG, "暂停成功：" + videoInfo.room_id);
        }
    }

    public void getLiveData() {
        mViewModel.getLiveVideoList();

//        TCVideoListMgr.getInstance().fetchLiveVideoList(getActivity(), new TCVideoListMgr.Listener() {
//            @Override
//            public void onVideoList(int retCode, ArrayList<TCVideoInfo> result, boolean refresh) {
//
//            }
//
//            @Override
//            public void onLiveVideoList(int retCode, ArrayList<VideoInfo> result, boolean refresh) {
//                onRefreshVideoList(retCode, result);
//            }
//        });
    }

    public void scrollToTop() {
        binding.recyclerView.scrollToPosition(0);
    }

    /**
     * 开始播放视频
     *
     * @param item 视频数据
     */
    private void startLivePlay(final VideoInfo item) {

        Intent intent = new Intent(getActivity(), TCAudienceActivity.class);
        intent.putExtra(TCConstants.PLAY_URL, item.rtmp_pull_url);
        intent.putExtra(TCConstants.PUSHER_ID, item.userId);
        if (item.pushers != null) {
            intent.putExtra(TCConstants.PUSHER_NAME, item.pushers.anchor_name);
            intent.putExtra(TCConstants.PUSHER_AVATAR, item.pushers.avatar);
        }
        intent.putExtra(TCConstants.HEART_COUNT, TextUtils.isEmpty(item.like_number) ? "0" : item.like_number);
        intent.putExtra(TCConstants.MEMBER_COUNT, item.viewer_count + "");
        intent.putExtra(TCConstants.GROUP_ID, item.room_id);
        intent.putExtra(TCConstants.PLAY_TYPE, item.meta_type);
        intent.putExtra(TCConstants.FILE_ID, "");
        intent.putExtra(TCConstants.COVER_PIC, item.frontcover);
        intent.putExtra(TCConstants.TIMESTAMP, item.live_start_time);
        intent.putExtra(TCConstants.ROOM_TITLE, item.live_title);
        startActivityForResult(intent, START_LIVE_PLAY);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onLoadMoreRequested() {
        getLiveData();
    }

    @Override
    public void onRefreshData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        mHomeAdapter.getData().clear();
        mHomeAdapter.notifyDataSetChanged();
        if (object == null) {
            mHomeAdapter.isUseEmpty(true);
            return;
        }

        VideoListResponse videoListResponse = (VideoListResponse) object;
        if (ContainerUtil.isEmpty(videoListResponse.rows)) {
            mHomeAdapter.isUseEmpty(true);
            return;
        }

        mHomeAdapter.isUseEmpty(false);
        mHomeAdapter.setNewData(videoListResponse.rows);
        mHomeAdapter.loadMoreComplete();
        mHomeAdapter.loadMoreEnd();
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
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetError() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (START_LIVE_PLAY == requestCode) {
            getLiveData();
        }
    }
}
