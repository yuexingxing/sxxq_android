package com.sanshao.bs.widget;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * 解决dialog展示不全问题
 * @Author yuexingxing
 * @time 2020/7/3
 */
public class BaseBottomSheetDialog extends BottomSheetDialog {

    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public BaseBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // for landscape mode
        BottomSheetBehavior behavior = getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
