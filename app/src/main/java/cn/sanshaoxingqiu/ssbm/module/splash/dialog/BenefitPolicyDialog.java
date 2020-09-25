package cn.sanshaoxingqiu.ssbm.module.splash.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.Res;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.ExerciseActivity;

/**
 * 商品海报
 *
 * @Author yuexingxing
 * @time 2020/9/24
 */
public class BenefitPolicyDialog {

    public void show(Context context, CommonCallBack commonCallBack) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_benefit_policy, null);
        Dialog dialog = new Dialog(context, R.style.dialogSupply);
        dialog.setContentView(rootView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        setPosition(dialog);

        TextView tvPolicy = rootView.findViewById(R.id.tv_policy);
        String str1 = "请您充分阅读并理解";
        String str2 = "《用户服务协议》";
        String str3 = "及";
        String str4 = "《用户隐私政策》";
        String str5 = "。";
        SpannableString spannableString = new SpannableString(str1 + str2 + str3 + str4 + str5);

        ClickableSpan spanPolicy1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                ExerciseActivity.start(context, "用户服务协议", Constants.userPolicyUrl);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan spanPolicy2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                ExerciseActivity.start(context, "用户隐私协议", Constants.userSecretUrl);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(spanPolicy1, str1.length(), (str1.length() + str2.length()), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        int start = (str1 + str2 + str3).length();
        int end = (str1 + str2 + str3 + str4).length();
        spannableString.setSpan(spanPolicy2, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_b6a57b)), str1.length(), (str1 + str2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Res.getColor(context, R.color.color_b6a57b)), (str1 + str2 + str3).length(),
                (str1 + str2 + str3 + str4).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvPolicy.setText(spannableString);
        tvPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        rootView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (commonCallBack != null) {
                    commonCallBack.callback(0, null);
                }
            }
        });
        rootView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (commonCallBack != null) {
                    commonCallBack.callback(1, null);
                }
            }
        });
    }

    public void setPosition(Dialog dialog) {

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
//        lp.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 0.7); // 宽度
//        lp.height = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getHeight() * 0.5); // 高度

        lp.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 0.7); // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }
}
