package com.sanshao.bs.module.shoppingcenter.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sanshao.bs.R;

/**
 * 商品说明弹窗
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class GoodsInroductionDialog {

    public void show(Context context) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_goods_introduction, null);
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(rootView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        rootView.findViewById(R.id.iv_close).setOnClickListener(v -> dialog.dismiss());
    }
}
