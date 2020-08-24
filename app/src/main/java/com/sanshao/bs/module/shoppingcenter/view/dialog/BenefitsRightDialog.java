package com.sanshao.bs.module.shoppingcenter.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sanshao.bs.R;

/**
 * 商品推荐弹窗
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class BenefitsRightDialog {

    public void show(Context context) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_benefits_right, null);
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(layout);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }
}
