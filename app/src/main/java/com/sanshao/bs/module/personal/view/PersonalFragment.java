package com.sanshao.bs.module.personal.view;

import android.graphics.Bitmap;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.log.XLog;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.PersonalFragmentBinding;
import com.sanshao.bs.module.TestMenuActivity;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.view.OrderListActivity;
import com.sanshao.bs.module.personal.adapter.PersonalOrderSubjectAdapter;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.income.view.IncomeMenuActivity;
import com.sanshao.bs.module.personal.inquiry.view.ToBeInquiryListActivity;
import com.sanshao.bs.module.personal.model.IPersonalCallBack;
import com.sanshao.bs.module.personal.personaldata.view.PersonalDetailActivity;
import com.sanshao.bs.module.personal.setting.view.SettingActivity;
import com.sanshao.bs.module.personal.viewmodel.PersonalViewModel;
import com.sanshao.bs.widget.flexible.OnReadyPullListener;
import com.sanshao.bs.widget.flexible.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class PersonalFragment extends BaseFragment<PersonalViewModel, PersonalFragmentBinding> implements IPersonalCallBack {

    private List<OrderInfo> mOrderInfoList = new ArrayList<>();
    private PersonalOrderSubjectAdapter mPersonalOrderSubjectAdapter;
    private PersonalViewModel mPersonalViewModel;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_fragment;
    }

    @Override
    public void initData() {

        mPersonalViewModel = new PersonalViewModel();
        binding.flexibleLayout.setHeader(binding.ivBg);
        binding.flexibleLayout.setReadyListener(new OnReadyPullListener() {
            @Override
            public boolean isReady() {
                XLog.d("zdddz", binding.nestedScrollview.getScrollY() + "");
                return binding.nestedScrollview.getScrollY() == 0;
            }
        }).setRefreshable(false)
                .setDefaultRefreshView(new OnRefreshListener() {
                    @Override
                    public void onRefreshing() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //刷新完成后需要调用onRefreshComplete()通知FlexibleLayout
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.flexibleLayout.onRefreshComplete();
                                    }
                                });
                            }
                        }).start();
                    }
                });
        binding.btnTest.setOnClickListener(v -> TestMenuActivity.start(getContext()));
        binding.llPersonal.setOnClickListener(v -> PersonalDetailActivity.start(getContext()));
        binding.includeOrder.rlAllOrder.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ALL));
        binding.includeOrder.llOrderTobepaid.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ToBePaid));
        binding.includeOrder.llOrderTobeuse.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ToBeUse));
        binding.includeOrder.llOrderTobeinquiry.setOnClickListener(v -> ToBeInquiryListActivity.start(context));
        binding.includeOrder.llOrderComplete.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.Complete));
        binding.pavIncome.setOnClickListener(v -> IncomeMenuActivity.start(context));
        binding.pavSetting.setOnClickListener(v -> SettingActivity.start(context));
        initOrderList();
        mPersonalViewModel.getUserInfo(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bitmap bitmap = ACache.get(context).getAsBitmap(ConfigSP.UserInfo.AVATAR);
        if (bitmap != null) {
            binding.ivAvatar.setImageBitmap(bitmap);
        }
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.transparent;
    }


    private void initOrderList() {

        mPersonalOrderSubjectAdapter = new PersonalOrderSubjectAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.includeOrder.recyclerView.setLayoutManager(linearLayoutManager);
        binding.includeOrder.recyclerView.setAdapter(mPersonalOrderSubjectAdapter);
        binding.includeOrder.recyclerView.setNestedScrollingEnabled(false);
        binding.includeOrder.recyclerView.setFocusable(false);
        mPersonalOrderSubjectAdapter.setOnItemClickListener(() -> {
            mPersonalOrderSubjectAdapter.setShowOpenView(false);
            mPersonalOrderSubjectAdapter.getData().clear();
            mPersonalOrderSubjectAdapter.addData(mOrderInfoList);
        });

        for (int i = 0; i < 2; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = i + "黄金微针你的美容必备，美容必备…";
            mOrderInfoList.add(orderInfo);
        }

//        binding.includeOrder.rlOpen.setOnClickListener(v -> {
//            binding.includeOrder.rlOpen.setVisibility(View.GONE);
//            mPersonalOrderSubjectAdapter.getData().clear();
//            mPersonalOrderSubjectAdapter.addData(mOrderInfoList);
//        });
        mPersonalOrderSubjectAdapter.addData(mOrderInfoList.get(0));
    }

    @Override
    public void returnUserInfo(UserInfo userInfo) {
        initLoginStatus(3);
    }

    @Override
    public void returnUpdateUserInfo() {

    }

    /**
     * 初始化登录状态
     */
    private void initLoginStatus(int state) {

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();

        //未登录
        if (state == 0) {
            binding.ivAvatar.setImageResource(R.drawable.image_graphofbooth_default);
            binding.rlAvatarBg.setBackground(null);
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_nostar_background));
            binding.tvName.setText("登录");
            binding.tvLabel.setText("游客");
            binding.ivZuan.setVisibility(View.GONE);
            binding.rlVipBg.setVisibility(View.INVISIBLE);
            return;
        }

        binding.tvName.setText(userInfo.nickName);
        //非会员
        if (state == 1) {
            binding.rlAvatarBg.setBackground(null);
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_nostar_background));
            binding.tvLabel.setText("普通会员");
            binding.rlVipBg.setVisibility(View.INVISIBLE);
        }
        //一星会员
        else if (state == 2) {
            binding.rlAvatarBg.setBackground(getResources().getDrawable(R.drawable.image_onestars));
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_onestarbg));
            binding.tvLabel.setText("一星会员");
            binding.rlVipBg.setVisibility(View.VISIBLE);
        }
        //二星会员
        else if (state == 3) {
            binding.rlAvatarBg.setBackground(getResources().getDrawable(R.drawable.image_twostars));
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_twostarsbg));
            binding.tvLabel.setText("二星会员");
            binding.rlVipBg.setVisibility(View.VISIBLE);
        }
        //三星会员
        else if (state == 4) {
            binding.rlAvatarBg.setBackground(getResources().getDrawable(R.drawable.image_threestars));
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_three_starsbg));
            binding.tvLabel.setText("三星会员");
            binding.rlVipBg.setVisibility(View.VISIBLE);
        }

    }
}