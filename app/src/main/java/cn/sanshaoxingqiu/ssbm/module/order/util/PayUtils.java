package cn.sanshaoxingqiu.ssbm.module.order.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chinapnr.android.adapay.AdaPay;
import com.chinapnr.android.adapay.bean.ResponseCode;
import com.exam.commonbiz.util.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.sanshaoxingqiu.ssbm.module.order.model.OnPayListener;
import cn.sanshaoxingqiu.ssbm.module.order.view.ConfirmPayActivity;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;
import com.exam.commonbiz.util.ToastUtil;

/**
 * 支付类
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class PayUtils {
    private final String TAG = PayUtils.class.getSimpleName();
    private IWXAPI api;
    private OnPayListener mOnPayListener;

    public PayUtils() {

    }

    public void registerApp(Context context) {
        api = WXAPIFactory.createWXAPI(context, Constants.WX_APPID);
        api.registerApp(Constants.WX_APPID);
//        api.handleIntent(getIntent(), this);
    }

    public PayUtils setOnPayListener(OnPayListener onPayListener) {
        mOnPayListener = onPayListener;
        return this;
    }

    /**
     * 开始支付
     */
    public PayUtils startPay(Activity activity, String payType, String orderInfo) {

        if (TextUtils.equals(ConfirmPayActivity.PAY_BY_ALI_APP, payType) && !ShareUtils.checkAliPayInstalled(activity)) {
            ToastUtil.showShortToast("请先安装支付宝客户端或选择其他方式支付");
            return this;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                AdaPay.doPay(activity, orderInfo, payResult -> {
                    if (payResult == null) {
                        ToastUtil.showShortToast("支付信息获取失败");
                        return;
                    }
                    switch (payResult.getResultCode()) {
                        case ResponseCode.SUCCESS:
                            Log.d(TAG, "支付成功");
                            if (mOnPayListener != null) {
                                mOnPayListener.onPaySuccess();
                            }
                            break;
                        case ResponseCode.PENDING:
                            Log.d(TAG, "取消支付");
                            break;
                        case ResponseCode.FAILED:
                            Log.d(TAG, "支付失败");
                            if (mOnPayListener != null) {
                                mOnPayListener.onPayFailed();
                            }
                            break;
                    }
                });
            }
        }).start();
        return this;
    }
}
