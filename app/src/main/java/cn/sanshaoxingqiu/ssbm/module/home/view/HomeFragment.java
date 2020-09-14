package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.audience.TCAudienceActivity;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;
import com.sanshao.livemodule.zhibo.playback.TCPlaybackActivity;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.HomeFragmentBinding;
import cn.sanshaoxingqiu.ssbm.util.Constants;

/**
 * 首页
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class HomeFragment extends BaseFragment<LiveViewModel, HomeFragmentBinding> implements ILiveRoomModel, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int START_LIVE_PLAY = 100;
    private HomeAdapter mHomeAdapter;
    private int mPageNum = 0;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return false;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.transparent_half;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initData() {

        mHomeAdapter = new HomeAdapter();
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
            mPageNum = 0;
            getLiveData();
        });

        binding.tvHomeTitle.setAlpha(0.26f);
        MLVBLiveRoomImpl.sharedInstance(SSApplication.getInstance());
        MLVBLiveRoomImpl.mInstance.initHttpRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void getLiveData() {
        mViewModel.getVideoList(mPageNum, Constants.PAGE_SIZE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (START_LIVE_PLAY == requestCode) {
                if (0 != resultCode) {
                    //观看直播返回错误信息后，刷新列表，但是不显示动画

                } else {
                    if (data == null) {
                        return;
                    }
                    //更新列表项的观看人数和点赞数
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onRefreshVideoList(final int retCode, final ArrayList<TCVideoInfo> result) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (retCode == 0) {
                        mHomeAdapter.getData().clear();
                        if (result != null) {
                            mHomeAdapter.addData((ArrayList<TCVideoInfo>) result.clone());
                        }
                    } else {
                        Toast.makeText(getActivity(), "刷新列表失败", Toast.LENGTH_LONG).show();
                    }
                    binding.tvHomeTitle.setText(String.format("直播（%s）", mHomeAdapter.getData().size()));
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
    private void startLivePlay(final TCVideoInfo item) {
        Intent intent;
        if (item.livePlay) {
            intent = new Intent(getActivity(), TCAudienceActivity.class);
            intent.putExtra(TCConstants.PLAY_URL, item.playUrl);
        } else {
            intent = new Intent(getActivity(), TCPlaybackActivity.class);
            intent.putExtra(TCConstants.PLAY_URL, TextUtils.isEmpty(item.hlsPlayUrl) ? item.playUrl : item.hlsPlayUrl);
        }

        intent.putExtra(TCConstants.PUSHER_ID, item.userId != null ? item.userId : "");
        intent.putExtra(TCConstants.PUSHER_NAME, TextUtils.isEmpty(item.nickname) ? item.userId : item.nickname);
        intent.putExtra(TCConstants.PUSHER_AVATAR, item.avatar);
        intent.putExtra(TCConstants.HEART_COUNT, "" + item.likeCount);
        intent.putExtra(TCConstants.MEMBER_COUNT, "" + item.viewerCount);
        intent.putExtra(TCConstants.GROUP_ID, item.groupId);
        intent.putExtra(TCConstants.PLAY_TYPE, item.livePlay);
        intent.putExtra(TCConstants.FILE_ID, item.fileId != null ? item.fileId : "");
        intent.putExtra(TCConstants.COVER_PIC, item.frontCover);
        intent.putExtra(TCConstants.TIMESTAMP, item.createTime);
        intent.putExtra(TCConstants.ROOM_TITLE, item.title);
        startActivityForResult(intent, START_LIVE_PLAY);
    }

    @Override
    public void returnGetLicense(LicenceInfo licenceInfo) {

    }

    @Override
    public void returnUserSign(UserSignResponse userSignResponse) {

    }

    @Override
    public void returnGetVideoList(List<TCVideoInfo> tcVideoInfoList) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (ContainerUtil.isEmpty(tcVideoInfoList)) {
            return;
        }

        if (mPageNum == 0) {
            mHomeAdapter.setNewData(tcVideoInfoList);
        } else {
            mHomeAdapter.addData(tcVideoInfoList);
        }

        binding.tvHomeTitle.setText(String.format("直播（%s）", mHomeAdapter.getData().size()));
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mPageNum++;
        getLiveData();
    }
}