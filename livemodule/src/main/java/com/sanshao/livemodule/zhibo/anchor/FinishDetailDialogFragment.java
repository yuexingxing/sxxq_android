package com.sanshao.livemodule.zhibo.anchor;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.GlideUtil;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.zhibo.live.StartLiveActivity;

/**
 * Module:   FinishDetailDialogFragment
 * <p>
 * Function: 推流结束的详情页
 * <p>
 * 统计了观看人数、点赞数量、开播时间
 */
public class FinishDetailDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog mDetailDialog = new Dialog(getActivity(), R.style.dialog);
        mDetailDialog.setContentView(R.layout.dialog_publish_detail);
        mDetailDialog.setCancelable(false);

        TextView tvCancel = (TextView) mDetailDialog.findViewById(R.id.anchor_btn_cancel);
        TextView tvContinue = (TextView) mDetailDialog.findViewById(R.id.anchor_btn_continue);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
                getActivity().finish();
            }
        });
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailDialog.dismiss();
                StartLiveActivity.start(getActivity());
                getActivity().finish();
            }
        });

        TextView tvName = (TextView) mDetailDialog.findViewById(R.id.tv_name);
        TextView tvDetailTime = (TextView) mDetailDialog.findViewById(R.id.tv_time);
        TextView tvDetailAdmires = (TextView) mDetailDialog.findViewById(R.id.tv_admires);
        TextView tvDetailWatchCount = (TextView) mDetailDialog.findViewById(R.id.tv_members);
        ImageView ivAvatar = mDetailDialog.findViewById(R.id.iv_avatar);

        //确认则显示观看detail
        tvName.setText(getArguments().getString("name"));
        tvDetailTime.setText(getArguments().getString("time"));
        tvDetailAdmires.setText(getArguments().getString("heartCount"));
        tvDetailWatchCount.setText(getArguments().getString("totalMemberCount"));
        GlideUtil.loadImage(getArguments().getString("avatar"), ivAvatar);

        return mDetailDialog;
    }
}
