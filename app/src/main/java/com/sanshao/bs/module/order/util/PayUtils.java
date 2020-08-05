package com.sanshao.bs.module.order.util;

import android.app.Activity;
import android.text.TextUtils;

import com.chinapnr.android.adapay.AdaPay;
import com.chinapnr.android.adapay.PayCallback;
import com.chinapnr.android.adapay.bean.PayResult;
import com.chinapnr.android.adapay.bean.ResponseCode;
import com.sanshao.bs.module.order.model.OnPayListener;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.ToastUtil;

/**
 * 支付类
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class PayUtils {

    private OnPayListener mOnPayListener;

    public PayUtils() {

    }

    public PayUtils setOnPayListener(OnPayListener onPayListener) {
        mOnPayListener = onPayListener;
        return this;
    }

    /**
     * 开始支付
     */
    public PayUtils startPay(Activity activity, String orderInfo) {

//        if (!CommandTools.isWeixinAvilible(activity)) {
//            ToastUtil.showShortToast("请先安装微信客户端或选择其他方式支付");
//            return this;
//        }

        AdaPay.doPay(activity, orderInfo, payResult -> {
            if (payResult == null) {
                return;
            }
            switch (payResult.getResultCode()) {
                case ResponseCode.SUCCESS:
                    if (mOnPayListener != null) {
                        mOnPayListener.onPaySuccess();
                    }
                    break;
                case ResponseCode.PENDING:
                    break;
                case ResponseCode.FAILED:
                    if (mOnPayListener != null) {
                        mOnPayListener.onPayFailed();
                    }
                    break;
            }
        });
        return this;
    }
}
