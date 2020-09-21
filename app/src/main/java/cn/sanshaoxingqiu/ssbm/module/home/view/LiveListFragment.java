package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.IBaseModel;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.audience.TCAudienceActivity;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoListMgr;

import java.util.ArrayList;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentLayoutLiveListBinding;
import cn.sanshaoxingqiu.ssbm.module.home.view.adapter.HomeAdapter;
import cn.sanshaoxingqiu.ssbm.util.Constants;

/**
 * 首页-直播列表
 *
 * @Author yuexingxing
 * @time 2020/9/16
 */
public class LiveListFragment extends BaseFragment<LiveViewModel, FragmentLayoutLiveListBinding> implements IBaseModel, BaseQuickAdapter.RequestLoadMoreListener {

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

        mViewModel.setIBaseModel(this);
        mHomeAdapter = new HomeAdapter();
        binding.emptyLayout.showSuccess();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mHomeAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerView);
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
//        mViewModel.getVideoList(mPageNum, Constants.PAGE_SIZE);

        TCVideoListMgr.getInstance().fetchLiveVideoList(getActivity(), new TCVideoListMgr.Listener() {
            @Override
            public void onVideoList(int retCode, ArrayList<TCVideoInfo> result, boolean refresh) {

            }

            @Override
            public void onLiveVideoList(int retCode, ArrayList<VideoInfo> result, boolean refresh) {
                onRefreshVideoList(retCode, result);
            }
        });
    }

    private void onRefreshVideoList(final int retCode, final ArrayList<VideoInfo> result) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (retCode == 0) {
                        mHomeAdapter.getData().clear();
                        if (result != null) {
                            mHomeAdapter.addData((ArrayList<VideoInfo>) result.clone());
                        }
                    } else {
                        Toast.makeText(getActivity(), "刷新列表失败", Toast.LENGTH_LONG).show();
                    }
                    if (mHomeAdapter.getData().size() > 0) {
                        binding.emptyLayout.showSuccess();
                    } else {
                        binding.emptyLayout.showEmpty("暂无直播", R.drawable.image_nolive);
                    }
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    /**
     * 开始播放视频
     *
     * @param item 视频数据
     */
    private void startLivePlay(final VideoInfo item) {

        Intent intent = new Intent(getActivity(), TCAudienceActivity.class);
        intent.putExtra(TCConstants.PLAY_URL, item.playUrl);
        intent.putExtra(TCConstants.PUSHER_ID, item.userId);
        intent.putExtra(TCConstants.PUSHER_NAME, item.nickname);
        intent.putExtra(TCConstants.PUSHER_AVATAR, item.avatar);
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
