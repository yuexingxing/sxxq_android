package cn.sanshaoxingqiu.ssbm.module.order.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.chinapnr.android.adapay.AdaPay;
import com.chinapnr.android.adapay.bean.ResponseCode;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.sanshaoxingqiu.ssbm.module.order.model.OnPayListener;
import cn.sanshaoxingqiu.ssbm.module.order.view.ConfirmPayActivity;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

/**
 * 支付类
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class PayUtils {

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

        AdaPay.doPay(activity, orderInfo, payResult -> {
            if (payResult == null) {
                ToastUtil.showShortToast("payResult=null");
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
