package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view;

import android.content.Context;
import android.view.View;

import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.dialog.CommonTipDialog;
import com.exam.commonbiz.util.Constants;

import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.view.ConfirmOrderActivity;
import cn.sanshaoxingqiu.ssbm.module.order.view.OrderListActivity;
import cn.sanshaoxingqiu.ssbm.module.register.view.RegisterActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.util.ShoppingCenterUtil;

public class GoodsPresenter {

    public static void startBuy(Context context, GoodsDetailInfo goodsDetailInfo) {
        if (goodsDetailInfo == null) {
            return;
        }
        if (!SSApplication.isLogin()) {
            LoginActivity.start(context);
            return;
        }
        UserInfo userInfo = BasicApplication.getUserInfo();
        if (goodsDetailInfo.isPayByMoney() && !goodsDetailInfo.isFree()) {
            ConfirmOrderActivity.start(context, goodsDetailInfo.sarti_id);
        } else if (goodsDetailInfo.isPayByPoint()) {

            //是星级会员
            if (userInfo.mem_class.isMember()) {
                //TODO 积分为0弹窗，点击跳到活动页
                if (userInfo.available_point == 0) {
                    CommonTipDialog commonTipDialog = new CommonTipDialog();
                    commonTipDialog.init(context)
                            .setTitle("分享金不足")
                            .setContent("啊哦，您的分享金不足，赶快邀请好友赚取分享金吧~")
                            .setLeftButton("取消")
                            .setOnLeftButtonClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonTipDialog.dismiss();
                                }
                            })
                            .showBottomLine(View.VISIBLE)
                            .setRightButton("获取分享金")
                            .setOnRightButtonClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonTipDialog.dismiss();
                                    ExerciseActivity.start(context, "一起拉用户", Constants.userUrl);
                                }
                            })
                            .show();
                } else {
                    ConfirmOrderActivity.start(context, goodsDetailInfo.sarti_id);
                }
            } else {
                CommonTipDialog commonTipDialog = new CommonTipDialog();
                commonTipDialog.init(context)
                        .setTitle("提示")
                        .setContent("您还不是我们的星级用户，分享好友不能领取分享金，是否立即购买项目成为星级用户？")
                        .setLeftButton("取消")
                        .setOnLeftButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                            }
                        })
                        .showBottomLine(View.VISIBLE)
                        .setRightButton("立即前往")
                        .setOnRightButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                                ExerciseActivity.start(context, "一起拉粉丝", Constants.fansUrl);
                            }
                        })
                        .show();
            }
            //TODO 先判断是不是星级会员

        } else {
            if (goodsDetailInfo.isFree() && userInfo.free_sarti_count < 1) {
                CommonTipDialog commonTipDialog = new CommonTipDialog();
                commonTipDialog.init(context)
                        .init(context)
                        .setTitle("提示")
                        .setContent("您已经成功领取一个免费变美专区项目，查看订单请至我的-订单列表查看")
                        .setLeftButton("取消")
                        .showBottomLine(View.VISIBLE)
                        .setOnLeftButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                            }
                        })
                        .setRightButton("查看订单")
                        .setOnRightButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                                OrderListActivity.start(context, OrderInfo.State.ALL);
                            }
                        })
                        .show();
                return;
            }
            ConfirmOrderActivity.start(context, goodsDetailInfo.sarti_id);
        }
    }
}
