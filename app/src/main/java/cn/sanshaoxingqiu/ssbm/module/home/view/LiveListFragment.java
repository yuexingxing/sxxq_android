package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.content.Intent;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.audience.TCAudienceActivity;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentLayoutLiveListBinding;
import cn.sanshaoxingqiu.ssbm.util.Constants;

/**
 * 首页-直播列表
 *
 * @Author yuexingxing
 * @time 2020/9/16
 */
public class LiveListFragment extends BaseFragment<LiveViewModel, FragmentLayoutLiveListBinding> implements ILiveRoomModel, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int START_LIVE_PLAY = 100;
    private HomeAdapter mHomeAdapter;
    private int mPageNum = 1;

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

        mViewModel.setILiveRoomModel(this);
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
        mViewModel.getVideoList(mPageNum, Constants.PAGE_SIZE);
    }

    /**
     * 开始播放视频
     *
     * @param item 视频数据
     */
    private void startLivePlay(final VideoInfo item) {

        Intent intent = new Intent(getActivity(), TCAudienceActivity.class);
        intent.putExtra(TCConstants.PLAY_URL, item.rtmp_pull_url);
        intent.putExtra(TCConstants.PUSHER_ID, item.pushers != null ? item.pushers.invitation_code : "");
        intent.putExtra(TCConstants.PUSHER_NAME, item.pushers.anchor_name);
        intent.putExtra(TCConstants.PUSHER_AVATAR, item.pushers.avatar);
        intent.putExtra(TCConstants.HEART_COUNT, TextUtils.isEmpty(item.like_number) ? "0" : item.like_number);
        intent.putExtra(TCConstants.MEMBER_COUNT, item.viewer_count);
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
    public void returnUserSign(UserSignResponse userSignResponse) {

    }

    @Override
    public void returnGetVideoList(VideoListResponse videoListResponse) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (videoListResponse == null || ContainerUtil.isEmpty(videoListResponse.rows)) {
            return;
        }

        if (mPageNum == 0) {
            mHomeAdapter.setNewData(videoListResponse.rows);
        } else {
            mHomeAdapter.addData(videoListResponse.rows);
        }

        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mPageNum++;
        getLiveData();
    }
}
