package com.sanshao.bs.module.personal.personaldata.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.exam.commonbiz.util.CommonCallBack;
import com.sanshao.bs.R;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.commonui.pickerview.builder.TimePickerBuilder;
import com.sanshao.commonui.pickerview.listener.OnTimeSelectChangeListener;
import com.sanshao.commonui.pickerview.listener.OnTimeSelectListener;
import com.sanshao.commonui.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author yuexingxing
 * @time 2020/7/3
 */
public class SelectBirthdayDialog {

    private TimePickerView mTimePickerView;
    private CommonCallBack mCommonCallBack;

    public SelectBirthdayDialog setCommonCallBack(CommonCallBack commonCallBack) {
        this.mCommonCallBack = commonCallBack;
        return this;
    }

    public SelectBirthdayDialog init(Context context, String title, boolean[] type) {//Dialog 模式下，在底部弹出

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(1900, 1, 1);
        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarShow = Calendar.getInstance();
        calendarShow.set(2000, 0, 1);
        mTimePickerView = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("pvTime", "onTimeSelect");
                if (mCommonCallBack != null){
                    mCommonCallBack.callback(0, CommandTools.getTime(date));
                }
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setTitleText(title)
                .setType(type)
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setDate(calendarShow)
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .setRangDate(calendarStart, calendarEnd)
                .build();

        Dialog mDialog = mTimePickerView.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            mTimePickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
        return this;
    }

    public SelectBirthdayDialog show() {
        if (mTimePickerView != null) {
            mTimePickerView.show();
        }
        return this;
    }
}
