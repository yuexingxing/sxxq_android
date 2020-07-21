package com.sanshao.bs.module.order.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.exam.commonbiz.util.CommonCallBack;
import com.sanshao.commonui.wheeldatepicker.dialog.DateTimeWheelDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class SelectSubscribeTimeDialog {

    private DateTimeWheelDialog mDateTimeWheelDialog;
    private CommonCallBack mCommonCallBack;

    public void setCommonCallBack(CommonCallBack commonCallBack) {
        this.mCommonCallBack = commonCallBack;
    }

    public void showDateDialog(Context context) {
        if (mDateTimeWheelDialog == null) {
            mDateTimeWheelDialog = createDialog(context);
        }
        mDateTimeWheelDialog.show();
    }

    private DateTimeWheelDialog createDialog(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date startDate = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2030);
        Date endDate = calendar.getTime();

        DateTimeWheelDialog dialog = new DateTimeWheelDialog(context);
        dialog.setShowCount(5);
        dialog.setItemVerticalSpace(24);
        dialog.show();
        dialog.setTitle("预约时间");
        dialog.configShowUI(DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE);
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确  认", new DateTimeWheelDialog.OnClickCallBack() {
            @Override
            public boolean callBack(View v, @NonNull Date selectedDate) {
                if (mCommonCallBack != null) {
                    mCommonCallBack.callback(0, SimpleDateFormat.getInstance().format(selectedDate));
                }
                return false;
            }
        });
        dialog.setDateArea(startDate, endDate, true);
        dialog.updateSelectedDate(new Date());
        return dialog;
    }
}
