package cn.sanshaoxingqiu.ssbm.module.personal.personaldata.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exam.commonbiz.util.Res;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.util.GlideUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class MyInviterDialog {

    public void show(Context context) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_my_inviter, null);

        LinearLayout llContent = rootView.findViewById(R.id.ll_content);
        TextView tvNoInviter = rootView.findViewById(R.id.tv_no_inviter);
        RelativeLayout rlBg = rootView.findViewById(R.id.rl_avatar_bg);
        RoundedImageView ivAvatar = rootView.findViewById(R.id.iv_avatar);
        TextView tvInviteCode = rootView.findViewById(R.id.tv_invite_code);
        TextView tvInviteName = rootView.findViewById(R.id.tv_name);
        TextView tvLabel = rootView.findViewById(R.id.tv_refer_grade);

        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(rootView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        if (userInfo == null || userInfo.referrer_mem == null
                || userInfo.referrer_mem.mem_class == null
        || TextUtils.isEmpty(userInfo.referrer_mem.nickname)){
            tvNoInviter.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
        } else{
            tvNoInviter.setVisibility(View.GONE);
            llContent.setVisibility(View.VISIBLE);

            tvInviteCode.setText("邀请码：" + userInfo.referrer_mem.invitation_code);
            tvInviteName.setText(userInfo.referrer_mem.nickname + "");
            GlideUtil.loadImage(userInfo.referrer_mem.avatar, ivAvatar);

            //一星会员
            if (TextUtils.equals(userInfo.referrer_mem.mem_class.mem_class_key, "1")) {
                tvLabel.setText("玉兔");
                rlBg.setBackground(Res.getDrawable(SSApplication.app, R.drawable.image_onestars));
            }
            //二星会员
            else if (TextUtils.equals(userInfo.referrer_mem.mem_class.mem_class_key, "2")) {
                tvLabel.setText("嫦娥");
                rlBg.setBackground(Res.getDrawable(SSApplication.app, R.drawable.image_twostars));
            }
            //三星会员
            else if (TextUtils.equals(userInfo.referrer_mem.mem_class.mem_class_key, "3")) {
                tvLabel.setText("悟空");
                rlBg.setBackground(Res.getDrawable(SSApplication.app, R.drawable.image_threestars));
            }
        }
        initView();

        rootView.findViewById(R.id.iv_close).setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void initView() {

    }
}
