package cn.sanshaoxingqiu.ssbm.module.personal.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.dialog.CommonTipDialog;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.Res;
import com.exam.commonbiz.util.ScreenUtil;
import com.exam.commonbiz.util.ToastUtil;
import com.sanshao.livemodule.zhibo.TCGlobalConfig;
import com.sanshao.livemodule.zhibo.live.AnchorInfoActivity;
import com.sanshao.livemodule.zhibo.login.TCLoginActivity;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.PersonalFragmentBinding;
import cn.sanshaoxingqiu.ssbm.module.TestMenuActivity;
import cn.sanshaoxingqiu.ssbm.module.invitation.view.InvitationActivity;
import cn.sanshaoxingqiu.ssbm.module.live.bean.LiveApplyResponse;
import cn.sanshaoxingqiu.ssbm.module.live.model.IIdentityModel;
import cn.sanshaoxingqiu.ssbm.module.live.view.IdentityingActivity;
import cn.sanshaoxingqiu.ssbm.module.live.view.LiveIdentifyActivity;
import cn.sanshaoxingqiu.ssbm.module.live.viewmodel.IdentityViewModel;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;
import cn.sanshaoxingqiu.ssbm.module.order.bean.AppointmentedInfo;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.model.IAppointmentModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderDetailModel;
import cn.sanshaoxingqiu.ssbm.module.order.view.OrderListActivity;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.AppointmentForConsultationViewModel;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderDetailViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.adapter.PersonalOrderSubjectAdapter;
import cn.sanshaoxingqiu.ssbm.module.personal.event.UpdateUserInfoEvent;
import cn.sanshaoxingqiu.ssbm.module.personal.inquiry.view.ToBeInquiryListActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.model.IPersonalCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.myfans.view.FansActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.personaldata.dialog.MyInviterDialog;
import cn.sanshaoxingqiu.ssbm.module.personal.personaldata.view.PersonalDetailActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.personaldata.view.RecommendCodeActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.setting.view.SettingActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.viewmodel.PersonalViewModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.util.ShoppingCenterUtil;
import cn.sanshaoxingqiu.ssbm.util.DateUtil;

/**
 * 我的
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class PersonalFragment extends BaseFragment<PersonalViewModel, PersonalFragmentBinding> implements IPersonalCallBack, IOrderDetailModel,
        IAppointmentModel, IIdentityModel {

    private List<OrderInfo> mOrderInfoList = new ArrayList<>();
    private PersonalOrderSubjectAdapter mPersonalOrderSubjectAdapter;
    private OrderDetailViewModel mOrderDetailViewModel;
    private AppointmentForConsultationViewModel mAppointmentForConsultationViewModel;
    private IdentityViewModel mIdentityViewModel;
    private UserInfo mUserInfo;
    private LiveApplyResponse mLiveApplyResponse;

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
        mIdentityViewModel = new IdentityViewModel();
        mOrderDetailViewModel.setCallBack(this);
        mAppointmentForConsultationViewModel.setCallBack(this);
        mIdentityViewModel.setCallBack(this);
        binding.nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > ScreenUtil.dp2px(context, 30)) {
                    binding.tvPersonalTitle.setBackgroundColor(Res.getColor(context, R.color.white));
                    if (mUserInfo != null && mUserInfo.mem_class != null
                            && TextUtils.equals("3", mUserInfo.mem_class.mem_class_key)) {
                        binding.tvPersonalTitle.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        binding.tvPersonalTitle.setTextColor(getResources().getColor(R.color.black));
                    }
                } else {
                    binding.tvPersonalTitle.setBackgroundColor(Res.getColor(context, R.color.transparent));
                    if (mUserInfo != null && mUserInfo.mem_class != null
                            && TextUtils.equals("3", mUserInfo.mem_class.mem_class_key)) {
                        binding.tvPersonalTitle.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        binding.tvPersonalTitle.setTextColor(getResources().getColor(R.color.black));
                    }
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
        binding.includePersonalOrder.llAllOrder.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
            } else {
                OrderListActivity.start(context, OrderInfo.State.ALL);
            }
        });
        binding.includePersonalOrder.llOrderTobepaid.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
            } else {
                OrderListActivity.start(context, OrderInfo.State.ToBePaid);
            }
        });
        binding.includePersonalOrder.llOrderTobeuse.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
            } else {
                OrderListActivity.start(context, OrderInfo.State.ToBeUse);
            }
        });
        binding.includePersonalOrder.llOrderTobeinquiry.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
            } else {
                ToBeInquiryListActivity.start(context);
            }
        });
        binding.includePersonalOrder.llOrderComplete.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
            } else {
                OrderListActivity.start(context, OrderInfo.State.Complete);
            }
        });
        binding.pavIncome.setOnClickListener(v -> ToastUtil.showShortToast("开发中"));
        binding.pavMyReferrer.setOnClickListener(v -> {
            new MyInviterDialog().show(context);
        });
        binding.pavMyFans.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
            } else {
                FansActivity.start(context);
            }
        });
        binding.pavMyShare.setOnClickListener(v -> {
            InvitationActivity.start(context, ShoppingCenterUtil.getInviteTagId());
        });
        binding.pavMyInviteCode.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
            } else {
                RecommendCodeActivity.start(context);
            }
        });
        binding.includePersonalLive.llLiveLive.setOnClickListener(v -> {
            if (!SSApplication.isLogin()) {
                LoginActivity.start(context);
                return;
            }
            if (mLiveApplyResponse == null) {
                return;
            }
//            mLiveApplyResponse.audit_status = LiveApplyResponse.AuditStatus.FAILED;
            //审核中
            if (mLiveApplyResponse.isAuditing()) {
                IdentityingActivity.start(context);
            }
            //认证成功
            else if (mLiveApplyResponse.isAuditSuccess()) {
                AnchorInfoActivity.start(context);
            }
            //未填写资料
            else if (mLiveApplyResponse.isUnApply()) {
                LiveIdentifyActivity.start(context);
            } else {
                CommonTipDialog commonTipDialog = new CommonTipDialog();
                commonTipDialog.init(context)
                        .setTitle("审核失败")
                        .setContent("原因: " + mLiveApplyResponse.reason)
                        .setContentColor(Res.getColor(context, R.color.color_c52d2d))
                        .setLeftButton("取消")
                        .showBottomLine(View.VISIBLE)
                        .setRightButton("重新申请")
                        .setOnLeftButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                            }
                        })
                        .setOnRightButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                                LiveIdentifyActivity.start(context);
                            }
                        })
                        .show();
            }

        });
        binding.includePersonalLive.llLiveLike.setOnClickListener(v -> {
            Intent intent = new Intent(context, TCLoginActivity.class);
            startActivity(intent);
        });

        initOrderList();
        binding.pavSetting.setOnClickListener(v -> SettingActivity.start(context));
        binding.guessYouLoveView.getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
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

    @Override
    protected void loadData() {
        mViewModel.getUserInfo();
        if (SSApplication.isLogin()) {
            mOrderDetailViewModel.getOrderNumStatus();
            mIdentityViewModel.getAnchorDetail();
            if (TCGlobalConfig.isUserSignEmpty()) {
                TCGlobalConfig.getUserSign();
            } else {
                TCUserMgr.getInstance().loginMLVB();
            }
        }
    }

    private void initOrderList() {

        mPersonalOrderSubjectAdapter = new PersonalOrderSubjectAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.includePersonalOrder.recyclerView.setLayoutManager(linearLayoutManager);
        binding.includePersonalOrder.recyclerView.setAdapter(mPersonalOrderSubjectAdapter);
        binding.includePersonalOrder.recyclerView.setNestedScrollingEnabled(false);
        binding.includePersonalOrder.recyclerView.setFocusable(false);
        mPersonalOrderSubjectAdapter.setOnItemClickListener(() -> {
            mPersonalOrderSubjectAdapter.setShowOpenView(false);
            mPersonalOrderSubjectAdapter.getData().clear();
            mPersonalOrderSubjectAdapter.addData(mOrderInfoList);
        });
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
        mUserInfo = userInfo;
        SSApplication.getInstance().saveUserInfo(userInfo);

        TCUserMgr tcUserMgr = TCUserMgr.getInstance();
        tcUserMgr.setAvatar(userInfo.avatar, null);
        tcUserMgr.setNickName(userInfo.nickname, null);
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
//        userInfo.mem_class.mem_class_key = "3";
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
        binding.ivZuan.setImageResource(R.drawable.icon_universaldrillmembers);
        binding.rlVipBg.setVisibility(View.VISIBLE);
        binding.viewOrderTopLine.setVisibility(View.VISIBLE);
        binding.pavMyInviteCode.setContent(userInfo.invitation_code);
        binding.pavMyShare.setContent(userInfo.point + "个");
        binding.pavMyFenrun.setContent("¥ " + userInfo.commission);

        String memberStartTime = DateUtil.timeFormat(userInfo.mem_class_start_date);
        int diffDays = DateUtil.getDiffDay(memberStartTime, DateUtil.getCurrentTime());
        binding.tvMembersDate.setText("会员期限" + diffDays
                + "/" + userInfo.mem_class.mem_class_valid_days);
        binding.tvMembersDateEnd.setText(DateUtil.getDateStr(memberStartTime, userInfo.mem_class.mem_class_valid_days));
        binding.progressHorizontal.setProgress(diffDays);
        binding.progressHorizontal.setMax(userInfo.mem_class.mem_class_valid_days);

        //一星会员
        binding.tvLabel.setText(userInfo.getMember());
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
            binding.tvName.setTextColor(getResources().getColor(R.color.white));
            binding.tvLabel.setTextColor(getResources().getColor(R.color.white));
            binding.tvPersonalTitle.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void returnOrderDetailInfo(GoodsDetailInfo orderDetailResponse) {

    }

    @Override
    public void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse) {
        if (orderNumStatusResponse == null) {
            return;
        }
        binding.includePersonalOrder.llOrderTobepaid.setUnReadNum(orderNumStatusResponse.pay);
        binding.includePersonalOrder.llOrderTobeuse.setUnReadNum(orderNumStatusResponse.paid);
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

    @Override
    public void returnLiveApply() {

    }

    @Override
    public void returnAnchorDetail(LiveApplyResponse liveApplyResponse) {
        if (liveApplyResponse == null) {
            return;
        }
        mLiveApplyResponse = liveApplyResponse;
        TCUserMgr.getInstance().setCoverPic(liveApplyResponse.frontcover, null);
    }
}