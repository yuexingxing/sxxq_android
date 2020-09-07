package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.exam.commonbiz.base.BaseFragment;
import com.sanshao.livemodule.zhibo.common.net.TCHTTPMgr;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoListMgr;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.HomeFragmentBinding;
import cn.sanshaoxingqiu.ssbm.module.home.viewmodel.HomeViewModel;
import cn.sanshaoxingqiu.ssbm.module.login.event.LoginEvent;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.event.UpdateUserInfoEvent;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

/**
 * 首页
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class HomeFragment extends BaseFragment<HomeViewModel, HomeFragmentBinding> {

    private HomeAdapter mHomeAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void loadData() {
        if (!TCHTTPMgr.getInstance().isLogin()) {
            UserInfo userInfo = SSApplication.getInstance().getUserInfo();
            if (userInfo != null && !TextUtils.isEmpty(userInfo.mem_phone)) {
                loginLive("sanshao" + userInfo.mem_phone, userInfo.mem_phone);
            }
        }
    }

    @Override
    public void initData() {

        mHomeAdapter = new HomeAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mHomeAdapter);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLiveData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void getLiveData() {
        TCVideoListMgr.getInstance().fetchLiveList(getActivity(), new TCVideoListMgr.Listener() {
            @Override
            public void onVideoList(int retCode, ArrayList<TCVideoInfo> result, boolean refresh) {
                onRefreshVideoList(retCode, result);
            }
        });
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
                    binding.tvHomeTitle.setText(String.format("直播(%s)", mHomeAdapter.getData().size()));
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void loginLive(String username, String password) {
        TCUserMgr tcLoginMgr = TCUserMgr.getInstance();
        tcLoginMgr.login(username, password, new TCHTTPMgr.Callback() {
            @Override
            public void onSuccess(JSONObject data) {
                ToastUtil.showLongToast("登录成功");
                getLiveData();
            }

            @Override
            public void onFailure(int code, final String msg) {
                ToastUtil.showLongToast(msg);
            }
        });
    }
}