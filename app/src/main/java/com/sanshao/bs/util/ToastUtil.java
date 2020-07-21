package com.sanshao.bs.util;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.exam.commonbiz.widget.CommonPopupWindow;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;


/**
 * Create By HaiyuKing
 * Used 简单的Toast封装类
 */
public class ToastUtil {

    private static CommonPopupWindow mCommonPopupWindow;
    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    /**
     * 短时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToast(String msg) {
        if (SSApplication.app != null) {
            if (toast == null) {
                toast = Toast.makeText(SSApplication.app, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            //1、setGravity方法必须放到这里，否则会出现toast始终按照第一次显示的位置进行显示（比如第一次是在底部显示，那么即使设置setGravity在中间，也不管用）
            //2、虽然默认是在底部显示，但是，因为这个工具类实现了中间显示，所以需要还原，还原方式如下：
            toast.setGravity(Gravity.BOTTOM, 0, dip2px(SSApplication.app, 64));
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastCenter(String msg) {
        if (SSApplication.app != null) {
            if (toast == null) {
                toast = Toast.makeText(SSApplication.app, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastTop(String msg) {
        if (SSApplication.app != null) {
            if (toast == null) {
                toast = Toast.makeText(SSApplication.app, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToast(String msg) {
        if (SSApplication.app != null) {
            if (toast == null) {
                toast = Toast.makeText(SSApplication.app, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.BOTTOM, 0, dip2px(SSApplication.app, 64));
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastCenter(String msg) {
        if (SSApplication.app != null) {
            if (toast == null) {
                toast = Toast.makeText(SSApplication.app, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastTop(String msg) {
        if (SSApplication.app != null) {
            if (toast == null) {
                toast = Toast.makeText(SSApplication.app, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    /*=================================常用公共方法============================*/
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void showDialogToast(Context context, View view, int drawable) {
        if (mCommonPopupWindow != null && mCommonPopupWindow.getPopupWindow().isShowing()) {
            mCommonPopupWindow.getPopupWindow().dismiss();
        }
        mCommonPopupWindow = new CommonPopupWindow(context, R.layout.popupwindow_layout_login_phone_tip, view.getWidth(), view.getHeight()) {
            @Override
            protected void initView() {
                View rootView = getContentView();
                ImageView imageView = rootView.findViewById(R.id.iv_icon);
                imageView.setImageResource(drawable);
            }

            @Override
            protected void initEvent() {

            }

            @Override
            protected void initWindow() {
                super.initWindow();
            }
        };
        mCommonPopupWindow.autoClose();
        CommonPopupWindow.LayoutGravity layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.TO_ABOVE);
        mCommonPopupWindow.showBashOfAnchor(view, layoutGravity, 0, 0);
    }

}