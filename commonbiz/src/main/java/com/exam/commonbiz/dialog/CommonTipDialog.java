package com.exam.commonbiz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.commonbiz.R;
import com.exam.commonbiz.util.CommonCallBack;

/**
 * 公共弹窗弹窗
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class CommonTipDialog {

    public void show(Context context, String title, String content, String leftButton, String rightButton, CommonCallBack commonCallBack) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_common_tip_dialog, null);
        Dialog dialog = new Dialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(layout);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        TextView tvTitle = layout.findViewById(R.id.tv_title);
        TextView tvContent = layout.findViewById(R.id.tv_content);
        TextView tvOk = layout.findViewById(R.id.tv_ok);
        TextView tvCancel = layout.findViewById(R.id.tv_cancel);

        tvTitle.setText(title);
        tvContent.setText(content);

        if (TextUtils.isEmpty(leftButton)) {
            tvOk.setVisibility(View.GONE);
        } else {
            tvOk.setVisibility(View.VISIBLE);
            tvOk.setText(leftButton);
        }

        if (TextUtils.isEmpty(rightButton)) {
            tvCancel.setVisibility(View.GONE);
        } else {
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setText(rightButton);
        }

        tvOk.setOnClickListener(v -> {
            dialog.dismiss();
            if (commonCallBack != null) {
                commonCallBack.callback(0, null);
            }
        });

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
            if (commonCallBack != null) {
                commonCallBack.callback(-1, null);
            }
        });
    }
}
