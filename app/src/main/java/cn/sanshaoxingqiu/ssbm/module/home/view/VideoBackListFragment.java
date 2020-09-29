package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.IBaseModel;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.audience.TCAudienceActivity;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.playback.TCPlaybackActivity;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentLayoutVideoBackListBinding;
import cn.sanshaoxingqiu.ssbm.module.home.view.adapter.HomeLiveAdapter;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;

/**
 * 首页-回放列表
 *
 * @Author yuexingxing
 * @time 2020/9/16
 */
public class VideoBackListFragment extends BaseFragment<LiveViewModel, FragmentLayoutVideoBackListBinding> implements IBaseModel, BaseQuickAdapter.RequestLoadMoreListener {
    private HomeLiveAdapter mHomeAdapter;
    private int mPageNum = 1;
    private TXLivePlayer mCurrentTXLivePlayer;
    private TXVodPlayer mCurrentTXVodPlayer;
    private int mVideoBackProgress = 0;
    private TXVodPlayConfig mTXVodPlayConfig = new TXVodPlayConfig();
    private String mCurrentPlayUrl;
    private List<VideoInfo> mVideoListData = new ArrayList<>();

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View emptyLayout = inflater.inflate(R.layout.item_layout_empty_live, null);
        emptyLayout.findViewById(R.id.ll_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum = 1;
                getLiveData();
            }
        });
        mHomeAdapter = new HomeLiveAdapter(mVideoListData);
        mHomeAdapter.setEmptyView(emptyLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mHomeAdapter);
        ((SimpleItemAnimator) binding.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
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
                    startPlayVideo();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        mHomeAdapter.setCommonCallBack(new CommonCallBack() {
            @Override
            public void callback(int postion, Object object) {
                if (!SSApplication.isLogin()) {
                    LoginActivity.start(context);
                    return;
                }
                startLivePlay(mHomeAdapter.getItem(postion));
            }
        });

        mHomeAdapter.setOnLoadMoreListener(this, binding.recyclerView);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            mPageNum = 1;
            getLiveData();
        });

        File sdcardDir = context.getExternalFilesDir(null);
        if (sdcardDir != null) {
            mTXVodPlayConfig.setCacheFolderPath(sdcardDir.getAbsolutePath() + "/sanshao");
        }
        mTXVodPlayConfig.setMaxCacheItems(5);

        getLiveData();
    }

    /**
     * 开始播放视频
     */
    private void startPlayVideo() {
        if (ContainerUtil.isEmpty(mVideoListData)) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = binding.recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            int firstVisiblePosition = linearManager.findFirstVisibleItemPosition();
            VideoInfo videoInfo = mVideoListData.get(firstVisiblePosition);
            if (videoInfo.isLive()) {
                if (mCurrentTXLivePlayer == null) {
                    mCurrentTXLivePlayer = new TXLivePlayer(context);
                } else {
                    mCurrentTXLivePlayer.pause();
                }
            } else {
                if (mCurrentTXVodPlayer == null) {
                    mCurrentTXVodPlayer = new TXVodPlayer(context);
                } else {
                    mCurrentTXVodPlayer.pause();
                }
            }

            View view = linearManager.findViewByPosition(firstVisiblePosition);
            if (null != binding.recyclerView.getChildViewHolder(view)) {
                BaseViewHolder viewHolder =
                        (BaseViewHolder) binding.recyclerView.getChildViewHolder(view);
                TXCloudVideoView txCloudVideoView = viewHolder.getView(R.id.anchor_video_view);
                ImageView ivLiveBg = view.findViewById(R.id.iv_bg);

                if (videoInfo.isLive()) {
                    mCurrentTXLivePlayer.setPlayerView(txCloudVideoView);
                    mCurrentTXLivePlayer.startPlay(videoInfo.flv_pull_url, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
                    ivLiveBg.setVisibility(View.GONE);
                    txCloudVideoView.setVisibility(View.VISIBLE);
                    mCurrentTXLivePlayer.setPlayListener(new ITXLivePlayListener() {
                        @Override
                        public void onPlayEvent(int event, Bundle bundle) {
                            if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
                                ivLiveBg.setVisibility(View.GONE);
                                mCurrentPlayUrl = videoInfo.flv_pull_url;
                                txCloudVideoView.setVisibility(View.VISIBLE);
                            } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                                ivLiveBg.setVisibility(View.VISIBLE);
                                txCloudVideoView.setVisibility(View.GONE);
                            } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT
                                    || event == TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL
                                    || event == TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND
                                    || event == TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL
                                    || event == TXLiveConstants.PLAY_ERR_HLS_KEY
                                    || event == TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL
                                    || event == TXLiveConstants.PLAY_ERR_STREAM_SWITCH_FAIL) {
                                ivLiveBg.setVisibility(View.VISIBLE);
                                txCloudVideoView.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onNetStatus(Bundle bundle) {

                        }
                    });
                    if (videoInfo.pushers != null) {
                        Log.d(TAG, "VideoBackListFragment-播放成功：" + videoInfo.pushers.anchor_name);
                    }
                } else {
                    mCurrentTXVodPlayer.setConfig(mTXVodPlayConfig);
                    mCurrentTXVodPlayer.startPlay(videoInfo.flv_pull_url);
                    mCurrentTXVodPlayer.setPlayerView(txCloudVideoView);
                    mCurrentTXVodPlayer.setVodListener(new ITXVodPlayListener() {
                        @Override
                        public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle bundle) {

                            int progress = bundle.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
                            int duration = bundle.getInt(TXLiveConstants.EVT_PLAY_DURATION);

                            int playProgress = 0;
                            if (progress != 0 && duration != 0) {
                                playProgress = progress * 100 / duration;
                            }

                            mVideoBackProgress = playProgress;
                            if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
                                ivLiveBg.setVisibility(View.GONE);
                                mCurrentPlayUrl = videoInfo.flv_pull_url;
                                txCloudVideoView.setVisibility(View.VISIBLE);
                            } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                                ivLiveBg.setVisibility(View.VISIBLE);
                                txCloudVideoView.setVisibility(View.GONE);
                            } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT
                                    || event == TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL
                                    || event == TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND
                                    || event == TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL
                                    || event == TXLiveConstants.PLAY_ERR_HLS_KEY
                                    || event == TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL
                                    || event == TXLiveConstants.PLAY_ERR_STREAM_SWITCH_FAIL) {
                                ivLiveBg.setVisibility(View.VISIBLE);
                                txCloudVideoView.setVisibility(View.GONE);
                            } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
                                txVodPlayer.startPlay(mCurrentPlayUrl);
                            }
                        }

                        @Override
                        public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

                        }
                    });
                }
            } else {
                Log.e(TAG, "view == null无法播放");
            }
        }
    }

    public void getLiveData() {
        mViewModel.getVideoBackList(mPageNum, Constants.PAGE_SIZE);
    }

    public void scrollToTop() {
        binding.recyclerView.scrollToPosition(0);
        onInVisible();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        Log.d(TAG, "VideoBackListFragment-onVisible");
        if (mCurrentTXLivePlayer != null) {
            mCurrentTXLivePlayer.resume();
            Log.d(TAG, "VideoBackListFragment-直播播放了");
        }
        if (mCurrentTXVodPlayer != null) {
            mCurrentTXVodPlayer.resume();
            Log.d(TAG, "VideoBackListFragment-直播播放了");
        }
    }

    @Override
    protected void onInVisible() {
        Log.d(TAG, "VideoBackListFragment-onInVisible");
        if (mCurrentTXLivePlayer != null) {
            mCurrentTXLivePlayer.pause();
            Log.d(TAG, "VideoBackListFragment-直播暂停了");
        }
        if (mCurrentTXVodPlayer != null) {
            mCurrentTXVodPlayer.pause();
            Log.d(TAG, "VideoBackListFragment-直播暂停了");
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onLoadMoreRequested() {
        mPageNum++;
        getLiveData();
        if (mCurrentTXVodPlayer != null) {
            mCurrentTXVodPlayer.pause();
        }
    }

    @Override
    public void onRefreshData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            mHomeAdapter.isUseEmpty(true);
            return;
        }

        VideoListResponse videoListResponse = (VideoListResponse) object;
        if (ContainerUtil.isEmpty(videoListResponse.rows)) {
            mHomeAdapter.isUseEmpty(true);
            return;
        }

        mVideoListData.clear();
        mVideoListData.addAll(videoListResponse.rows);
        mHomeAdapter.notifyDataSetChanged();

        mHomeAdapter.isUseEmpty(false);
        mHomeAdapter.loadMoreComplete();
        mHomeAdapter.isUseEmpty(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPlayVideo();
            }
        }, 500);
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

        mVideoListData.addAll(videoListResponse.rows);
        mHomeAdapter.notifyDataSetChanged();
//        mHomeAdapter.addData(videoListResponse.rows);
        mHomeAdapter.loadMoreComplete();
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetError() {

    }

    /**
     * 开始播放视频
     *
     * @param item 视频数据
     */
    private void startLivePlay(final VideoInfo item) {

        if (mCurrentTXVodPlayer != null) {
            mCurrentTXVodPlayer.pause();
        }
        if (mCurrentTXLivePlayer != null) {
            mCurrentTXLivePlayer.pause();
        }

        Intent intent = new Intent(getActivity(), TCPlaybackActivity.class);
        if (item.isLive()) {
            intent = new Intent(getActivity(), TCAudienceActivity.class);
        } else {
            int playPosition = 0;
            if (mCurrentTXVodPlayer != null) {
                mVideoBackProgress = mVideoBackProgress - 2;
                if (mVideoBackProgress < 0 || mVideoBackProgress > 100) {
                    mVideoBackProgress = 0;
                }
                playPosition = mVideoBackProgress;
            }
            intent.putExtra(TCConstants.PLAY_POSITION, playPosition);
        }
        intent.putExtra(TCConstants.PUSHER_ID, item.pushers.invitation_code);
        intent.putExtra(TCConstants.PLAY_URL, item.rtmp_pull_url);
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
        startActivity(intent);
    }
}
