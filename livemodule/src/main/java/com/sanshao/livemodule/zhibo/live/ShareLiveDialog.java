package com.sanshao.livemodule.zhibo.live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.exam.commonbiz.util.CommonCallBack;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sanshao.livemodule.R;

/**
 * 分享直播间
 *
 * @Author yuexingxing
 * @time 2020/9/18
 */
public class ShareLiveDialog {

    public void show(Context context, final CommonCallBack callBack) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_share_live, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);

        rootView.findViewById(R.id.ll_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (callBack != null) {
                    callBack.callback(0, null);
                }
            }
        });

        rootView.findViewById(R.id.ll_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (callBack != null) {
                    callBack.callback(1, null);
                }
            }
        });

        rootView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(rootView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }
}
