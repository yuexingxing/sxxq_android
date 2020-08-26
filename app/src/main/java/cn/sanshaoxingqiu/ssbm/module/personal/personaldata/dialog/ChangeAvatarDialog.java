package cn.sanshaoxingqiu.ssbm.module.personal.personaldata.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.exam.commonbiz.util.CommonCallBack;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class ChangeAvatarDialog {

    public void show(Context context, CommonCallBack commonCallBack) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_change_avatar, null);
        ImageView ivClose = rootView.findViewById(R.id.iv_close);
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(rootView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        rootView.findViewById(R.id.ll_takephoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (commonCallBack != null) {
                    commonCallBack.callback(0, null);
                }
            }
        });
        rootView.findViewById(R.id.ll_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (commonCallBack != null) {
                    commonCallBack.callback(1, null);
                }
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
