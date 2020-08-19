package com.sanshao.bs.module.invitation.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.CommonCallBack;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityInvitationBinding;
import com.sanshao.bs.module.invitation.bean.UserReferrals;
import com.sanshao.bs.module.invitation.model.InvitationCallBack;
import com.sanshao.bs.module.invitation.viewmodel.InvitationViewModel;
import com.sanshao.bs.module.personal.myfans.view.FansListAdapter;
import com.sanshao.bs.module.register.view.RegisterActivity;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsTypeInfo;
import com.sanshao.bs.module.shoppingcenter.view.GoodsDetailActivity;
import com.sanshao.bs.module.shoppingcenter.view.GoodsListActivity;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GoodsTypeDetailVerticalAdapter;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InvitationActivity extends BaseActivity<InvitationViewModel, ActivityInvitationBinding> implements InvitationCallBack {

    private GoodsTypeDetailVerticalAdapter goodsTypeDetailVerticalAdapter;

    private InvitationListAdapter invitationListAdapter;

    public static void start(Context context, String artiTagId) {
        if (TextUtils.isEmpty(artiTagId)) return;
        Intent starter = new Intent(context, InvitationActivity.class);
        starter.putExtra(Constants.OPT_DATA, artiTagId);
        context.startActivity(starter);
    }

    @Override
    public void initData() {
        String artiTagId = getIntent().getStringExtra(Constants.OPT_DATA);

        mViewModel.setCallBack(this);

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        binding.ivInvitation.setOnClickListener(v -> {
            // TODO
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(InvitationActivity.this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        goodsTypeDetailVerticalAdapter = new GoodsTypeDetailVerticalAdapter();
        goodsTypeDetailVerticalAdapter.setCommonCallBack((postion, object) -> {
            if (SSApplication.isLogin()) {
                GoodsTypeInfo goodsTypeInfo = new GoodsTypeInfo();
                goodsTypeInfo.artitag_id = Constants.TAG_ID_INVITE;
                GoodsListActivity.start(context, goodsTypeInfo);
            } else {
                RegisterActivity.start(context, Constants.TAG_ID_REGISTER);
            }
        });
        if (!SSApplication.isLogin()) {
            goodsTypeDetailVerticalAdapter.isShowConver(true);
        }
        goodsTypeDetailVerticalAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!SSApplication.isLogin()) {
                RegisterActivity.start(context, Constants.TAG_ID_REGISTER);
                return;
            }
            GoodsDetailInfo goodsDetailInfo = goodsTypeDetailVerticalAdapter.getData().get(position);
            GoodsDetailActivity.start(view.getContext(), goodsDetailInfo.sarti_id);
        });
        binding.goodsRecyclerView.setNestedScrollingEnabled(false);
        binding.goodsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.goodsRecyclerView.setAdapter(goodsTypeDetailVerticalAdapter);


        invitationListAdapter = new InvitationListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.invitationRecyclerView.setNestedScrollingEnabled(false);
        binding.invitationRecyclerView.setLayoutManager(linearLayoutManager);
        binding.invitationRecyclerView.setAdapter(invitationListAdapter);

        LoadDialogMgr.getInstance().show(InvitationActivity.this);
        mViewModel.getGoodsList(artiTagId);
        mViewModel.getUserReferrals();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invitation;
    }

    @Override
    public void showGoods(List<GoodsDetailInfo> goodsList) {
        if (goodsList == null || goodsList.size() == 0) {
            binding.viewActivityTitle.setVisibility(View.GONE);
            binding.goodsRecyclerView.setVisibility(View.GONE);
            return;
        }
        goodsTypeDetailVerticalAdapter.setNewData(goodsList);
    }

    @Override
    public void showUserReferrals(UserReferrals userReferrals) {
        if (userReferrals == null || userReferrals.referrals == null || userReferrals.referrals.size() == 0) {
            return;
        }
        int count = userReferrals.referrals.size();
        binding.invitationCount.setVisibility(View.VISIBLE);
        binding.invitationCount.setText("已邀请" + count + "人");

        binding.invitationTip.setVisibility(View.VISIBLE);
        binding.invitationTip.setText("已成功邀请" + count + "个好友  可免费领取" + count + "个奖励变美专区项目");

        binding.viewUser1.setVisibility(View.GONE);
        binding.viewUser2.setVisibility(View.GONE);
        binding.viewUser2.setVisibility(View.GONE);
        if (count > 0) {
            UserReferrals.UserReferralsItem item = userReferrals.referrals.get(0);
            binding.viewUser1.setVisibility(View.VISIBLE);
            binding.tvUserName1.setText(item.nickname);
            if (!TextUtils.isEmpty(item.avatar)) {
                GlideUtil.loadImage(item.avatar, binding.ivUserAvatar1, R.drawable.image_placeholder_two);
            }
        }

        if (count > 1) {
            UserReferrals.UserReferralsItem item = userReferrals.referrals.get(1);
            binding.viewUser2.setVisibility(View.VISIBLE);
            binding.tvUserName2.setText(item.nickname);
            if (!TextUtils.isEmpty(item.avatar)) {
                GlideUtil.loadImage(item.avatar, binding.ivUserAvatar2, R.drawable.image_placeholder_two);
            }
        }

        if (count > 2) {
            UserReferrals.UserReferralsItem item = userReferrals.referrals.get(2);
            binding.viewUser3.setVisibility(View.VISIBLE);
            binding.tvUserName3.setText(item.nickname);
            if (!TextUtils.isEmpty(item.avatar)) {
                GlideUtil.loadImage(item.avatar, binding.ivUserAvatar3, R.drawable.image_placeholder_two);
            }
        }

        binding.invitaionRecord.setVisibility(View.VISIBLE);
        invitationListAdapter.setNewData(userReferrals.referrals);
    }
}
