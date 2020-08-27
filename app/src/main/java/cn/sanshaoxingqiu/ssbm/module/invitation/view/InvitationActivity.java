package cn.sanshaoxingqiu.ssbm.module.invitation.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityInvitationBinding;
import cn.sanshaoxingqiu.ssbm.module.invitation.bean.UserReferrals;
import cn.sanshaoxingqiu.ssbm.module.invitation.model.InvitationCallBack;
import cn.sanshaoxingqiu.ssbm.module.invitation.viewmodel.InvitationViewModel;
import cn.sanshaoxingqiu.ssbm.module.register.view.RegisterActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsTypeInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.GoodsDetailActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.GoodsListActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.GoodsTypeDetailVerticalAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import cn.sanshaoxingqiu.ssbm.util.BitmapUtil;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.GlideUtil;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InvitationActivity extends BaseActivity<InvitationViewModel, ActivityInvitationBinding> implements InvitationCallBack {

    private GoodsTypeDetailVerticalAdapter goodsTypeDetailVerticalAdapter;
    private InvitationListAdapter invitationListAdapter;
    private String mArtiTagId;

    public static void start(Context context, String artiTagId) {
        if (TextUtils.isEmpty(artiTagId)) return;
        Intent starter = new Intent(context, InvitationActivity.class);
        starter.putExtra(Constants.OPT_DATA, artiTagId);
        context.startActivity(starter);
    }

    @Override
    public void initData() {

        mArtiTagId = getIntent().getStringExtra(Constants.OPT_DATA);
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
            GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
            goodsDetailInfo.sarti_name = "超多福利！价值6400元的医美项目，限时90元领取！";
            goodsDetailInfo.sarti_intro = "";
            goodsDetailInfo.thumbnail_img = "https://ssbm-wxapp-static.oss-cn-shanghai.aliyuncs.com/icon/mianfeibm.jpeg";
            share(goodsDetailInfo);
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
        mViewModel.getGoodsList(mArtiTagId);
        if (SSApplication.isLogin()) {
            mViewModel.getUserReferrals();
        }
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
        binding.invitationCount.setText("已邀请" + count + "人  点亮" + count + "个分享金");

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

    private void share(GoodsDetailInfo goodsDetailInfo) {

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo("分享到微信"));
//        commonDialogInfoList.add(new CommonDialogInfo("生成海报"));

        new CommonBottomDialog()
                .init(this)
                .setData(commonDialogInfoList)
                .setOnItemClickListener(commonDialogInfo -> {
                    if (commonDialogInfo.position == 0) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap bitmap = BitmapUtil.getBitmap(goodsDetailInfo.thumbnail_img);
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constants.OPT_DATA, goodsDetailInfo);
                                message.setData(bundle);
                                message.obj = bitmap;
                                mHandler.sendMessage(message);
                            }
                        }).start();
                    } else {
                        new GoodsPosterDialog().show(context, new GoodsDetailInfo());
                    }
                })
                .show();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            GoodsDetailInfo goodsDetailInfo = (GoodsDetailInfo) bundle.getSerializable(Constants.OPT_DATA);
            Bitmap bitmap = (Bitmap) message.obj;
            new ShareUtils()
                    .init(context)
                    .shareMiniProgram(goodsDetailInfo.sarti_name, goodsDetailInfo.sarti_desc,
                            bitmap, goodsDetailInfo.getRegistrationSharePath(mArtiTagId));
        }
    };
}
