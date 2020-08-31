package cn.sanshaoxingqiu.ssbm.module.live.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.exam.commonbiz.util.CommonCallBack;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * 身份认证失败
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class IdentityFailedDialog {

    public void show(Context context, CommonCallBack commonCallBack) {
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_identity_failed, null);
        Dialog dialog = new Dialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(layout);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        layout.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.tv_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (commonCallBack != null) {
                    commonCallBack.callback(0, null);
                }
            }
        });
    }
}
