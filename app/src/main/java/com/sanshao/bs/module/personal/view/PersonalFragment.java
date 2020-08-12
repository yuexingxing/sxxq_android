package com.sanshao.bs.module.personal.view;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.PersonalFragmentBinding;
import com.sanshao.bs.module.TestMenuActivity;
import com.sanshao.bs.module.login.view.LoginActivity;
import com.sanshao.bs.module.order.bean.AppointmentedInfo;
import com.sanshao.bs.module.order.bean.OrderDetailResponse;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
import com.sanshao.bs.module.order.model.IAppointmentModel;
import com.sanshao.bs.module.order.model.IOrderDetailModel;
import com.sanshao.bs.module.order.view.OrderListActivity;
import com.sanshao.bs.module.order.viewmodel.AppointmentForConsultationViewModel;
import com.sanshao.bs.module.order.viewmodel.OrderDetailViewModel;
import com.sanshao.bs.module.personal.adapter.PersonalOrderSubjectAdapter;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.event.UpdateUserInfoEvent;
import com.sanshao.bs.module.personal.income.view.IncomeHomeActivity;
import com.sanshao.bs.module.personal.inquiry.view.ToBeInquiryListActivity;
import com.sanshao.bs.module.personal.model.IPersonalCallBack;
import com.sanshao.bs.module.personal.personaldata.view.PersonalDetailActivity;
import com.sanshao.bs.module.personal.setting.view.SettingActivity;
import com.sanshao.bs.module.personal.viewmodel.PersonalViewModel;
import com.sanshao.bs.util.DateUtil;
import com.sanshao.bs.util.GlideUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class PersonalFragment extends BaseFragment<PersonalViewModel, PersonalFragmentBinding> implements IPersonalCallBack, IOrderDetailModel, IAppointmentModel {

    private List<OrderInfo> mOrderInfoList = new ArrayList<>();
    private PersonalOrderSubjectAdapter mPersonalOrderSubjectAdapter;
    private OrderDetailViewModel mOrderDetailViewModel;
    private AppointmentForConsultationViewModel mAppointmentForConsultationViewModel;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_fragment;
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
        mOrderDetailViewModel = new OrderDetailViewModel();
        mAppointmentForConsultationViewModel = new AppointmentForConsultationViewModel();
        mOrderDetailViewModel.setCallBack(this);
        mAppointmentForConsultationViewModel.setCallBack(this);
        binding.nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > ScreenUtil.dp2px(context, 30)) {
                    binding.tvPersonalTitle.setBackgroundColor(Res.getColor(context, R.color.white));
                } else {
                    binding.tvPersonalTitle.setBackgroundColor(Res.getColor(context, R.color.transparent));
                }
            }
        });
        binding.btnTest.setOnClickListener(v -> TestMenuActivity.start(getContext()));
        binding.llPersonal.setOnClickListener(v -> {
            if (TextUtils.isEmpty(SSApplication.getToken())) {
                LoginActivity.start(context);
            } else {
                PersonalDetailActivity.start(getContext());
            }
        });
        binding.includeOrder.llAllOrder.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ALL));
        binding.includeOrder.llOrderTobepaid.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ToBePaid));
        binding.includeOrder.llOrderTobeuse.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ToBeUse));
        binding.includeOrder.llOrderTobeinquiry.setOnClickListener(v -> ToBeInquiryListActivity.start(context));
        binding.includeOrder.llOrderComplete.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.Complete));
        binding.pavIncome.setOnClickListener(v -> IncomeHomeActivity.start(context));
        binding.pavSetting.setOnClickListener(v -> SettingActivity.start(context));
        initOrderList();
        mViewModel.getUserInfo();
        binding.guessYouLoveView.getData();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!TextUtils.isEmpty(SSApplication.getToken())) {
        mViewModel.getUserInfo();
        mOrderDetailViewModel.getOrderNumStatus();
        mAppointmentForConsultationViewModel.getAppointmentedList();
//        } else {
//            initMemberStatus(null);
//        }
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
    protected void loadData() {

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

//        for (int i = 0; i < 2; i++) {
//            OrderInfo orderInfo = new OrderInfo();
//            orderInfo.name = i + "黄金微针你的美容必备，美容必备…";
//            mOrderInfoList.add(orderInfo);
//        }

//        binding.includeOrder.rlOpen.setOnClickListener(v -> {
//            binding.includeOrder.rlOpen.setVisibility(View.GONE);
//            mPersonalOrderSubjectAdapter.getData().clear();
//            mPersonalOrderSubjectAdapter.addData(mOrderInfoList);
//        });
//        mPersonalOrderSubjectAdapter.addData(mOrderInfoList.get(0));
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUserInfoEvent(UpdateUserInfoEvent updateUserInfoEvent) {
        mViewModel.getUserInfo();
    }

    @Override
    public void returnUserInfo(UserInfo userInfo) {
        initMemberStatus(userInfo);
        if (userInfo == null) {
            return;
        }
        SSApplication.getInstance().saveUserInfo(userInfo);
    }

    @Override
    public void returnUpdateUserInfo(UserInfo userInfo) {

    }

    /**
     * 初始化会员状态
     */
    private void initMemberStatus(UserInfo userInfo) {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.flHeader.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsLabel = (RelativeLayout.LayoutParams) binding.tvLabel.getLayoutParams();

        //未登录
        if (userInfo == null) {
            binding.ivAvatar.setImageResource(R.drawable.image_graphofbooth_default);
            binding.rlAvatarBg.setBackground(null);
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_nostar_background));
            binding.tvName.setText("登录");
            binding.tvLabel.setText("游客");
            binding.ivZuan.setVisibility(View.GONE);
            binding.rlVipBg.setVisibility(View.INVISIBLE);
            binding.viewSpaceZuan.setVisibility(View.GONE);
            binding.viewOrderTopLine.setVisibility(View.GONE);

            layoutParamsLabel.setMargins(ScreenUtil.dp2px(context, 10), 0, ScreenUtil.dp2px(context, 10), 0);
            binding.tvLabel.setLayoutParams(layoutParamsLabel);

            layoutParams.height = ScreenUtil.dp2px(context, 215);
            binding.flHeader.setLayoutParams(layoutParams);
            return;
        }

        binding.tvName.setText(userInfo.nickname);
        binding.ivZuan.setVisibility(View.VISIBLE);

        GlideUtil.loadImage(userInfo.avatar, binding.ivAvatar, R.drawable.image_placeholder_two);

//        userInfo.mem_class = new MemberClassInfo();
//        userInfo.mem_class.mem_class_key = "1";
//        userInfo.mem_class.mem_class_name = "一星会员";
//        userInfo.mem_class = null;

        //非会员
        if (userInfo.mem_class == null || null == userInfo.mem_class.mem_class_key) {
            binding.rlAvatarBg.setBackground(null);
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_nostar_background));
            binding.tvLabel.setText("普通会员");
            binding.ivZuan.setImageResource(R.drawable.icon_commondiamond);
            binding.rlVipBg.setVisibility(View.INVISIBLE);
            binding.viewSpaceZuan.setVisibility(View.VISIBLE);
            binding.viewOrderTopLine.setVisibility(View.GONE);

            layoutParams.height = ScreenUtil.dp2px(context, 235);
            binding.flHeader.setLayoutParams(layoutParams);
            return;
        }

        layoutParams.height = ScreenUtil.dp2px(context, 295);
        binding.flHeader.setLayoutParams(layoutParams);
        binding.tvLabel.setText(userInfo.mem_class.mem_class_name);
        binding.ivZuan.setImageResource(R.drawable.icon_universaldrillmembers);
        binding.rlVipBg.setVisibility(View.VISIBLE);
        binding.viewOrderTopLine.setVisibility(View.VISIBLE);

        String memberStartTime = DateUtil.timeFormat(userInfo.mem_class_start_date);
        int diffDays = DateUtil.getDiffDay(memberStartTime, DateUtil.getCurrentTime());
        binding.tvMembersDate.setText("会员期限" + diffDays
                + "/" + userInfo.mem_class.mem_class_valid_days);
        binding.tvMembersDateEnd.setText(DateUtil.getDateStr(memberStartTime, userInfo.mem_class.mem_class_valid_days));
        binding.progressHorizontal.setProgress(diffDays);
        binding.progressHorizontal.setMax(userInfo.mem_class.mem_class_valid_days);

        //一星会员
        if (TextUtils.equals(userInfo.mem_class.mem_class_key, "1")) {
            binding.rlAvatarBg.setBackground(getResources().getDrawable(R.drawable.image_onestars));
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_onestarbg));
        }
        //二星会员
        else if (TextUtils.equals(userInfo.mem_class.mem_class_key, "2")) {
            binding.rlAvatarBg.setBackground(getResources().getDrawable(R.drawable.image_twostars));
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_twostarsbg));
        }
        //三星会员
        else if (TextUtils.equals(userInfo.mem_class.mem_class_key, "3")) {
            binding.rlAvatarBg.setBackground(getResources().getDrawable(R.drawable.image_threestars));
            binding.ivBg.setBackground(getResources().getDrawable(R.drawable.image_three_starsbg));
        }
    }

    @Override
    public void returnOrderDetailInfo(OrderDetailResponse orderDetailResponse) {

    }

    @Override
    public void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse) {
        if (orderNumStatusResponse == null) {
            return;
        }
        binding.includeOrder.llOrderTobepaid.setUnReadNum(orderNumStatusResponse.pay);
        binding.includeOrder.llOrderTobeuse.setUnReadNum(orderNumStatusResponse.paid);
    }

    @Override
    public void returnCancelOrder() {

    }

    @Override
    public void returnAppointmentedList(AppointmentedInfo appointmentedInfo) {

    }

    @Override
    public void onRefreshData(Object object) {

    }

    @Override
    public void onLoadMoreData(Object object) {

    }

    @Override
    public void onNetError() {

    }
}