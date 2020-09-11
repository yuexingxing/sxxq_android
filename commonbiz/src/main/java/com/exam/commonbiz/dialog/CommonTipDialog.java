package com.exam.commonbiz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.commonbiz.R;

/**
 * 公共弹窗弹窗
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class CommonTipDialog {

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvLeft;
    private TextView tvRight;
    private Dialog mDialog;
    private View mViewBottomLine;

    public CommonTipDialog init(Context context) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_common_tip_dialog, null);
        mDialog = new Dialog(context, R.style.BottomSheetDialog);
        mDialog.setContentView(layout);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);

        tvTitle = layout.findViewById(R.id.tv_title);
        tvContent = layout.findViewById(R.id.tv_content);
        tvLeft = layout.findViewById(R.id.tv_ok);
        tvRight = layout.findViewById(R.id.tv_cancel);
        mViewBottomLine = layout.findViewById(R.id.view_bottom_line);

        tvLeft.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        mViewBottomLine.setVisibility(View.GONE);

        setDialog(mDialog);
        return this;
    }

    public CommonTipDialog setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public CommonTipDialog setContent(String content) {
        tvContent.setText(content);
        return this;
    }

    public CommonTipDialog setContentColor(int color) {
        tvContent.setTextColor(color);
        return this;
    }

    public CommonTipDialog setLeftButton(String leftButton) {
        if (TextUtils.isEmpty(leftButton)) {
            tvLeft.setVisibility(View.GONE);
        } else {
            tvLeft.setVisibility(View.VISIBLE);
            tvLeft.setText(leftButton);
        }
        return this;
    }

    public CommonTipDialog setRightButton(String rightButton) {
        if (TextUtils.isEmpty(rightButton)) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(rightButton);
        }
        return this;
    }

    public CommonTipDialog showBottomLine(int visible) {
        mViewBottomLine.setVisibility(visible);
        return this;
    }

    public CommonTipDialog setOnLeftButtonClick(View.OnClickListener onClickListener) {
        tvLeft.setOnClickListener(onClickListener);
        return this;
    }

    public CommonTipDialog setOnRightButtonClick(View.OnClickListener onClickListener) {
        tvRight.setOnClickListener(onClickListener);
        return this;
    }

    public CommonTipDialog show() {
        if (mDialog != null) {
            mDialog.show();
        }
        return this;
    }

    public CommonTipDialog dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        return this;
    }

    public void setDialog(Dialog dialog) {

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        layoutParams.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(layoutParams);
    }
}
