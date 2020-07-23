package com.sanshao.bs.module.order.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.order.model.OnPayListener;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.bs.wxapi.alipay.AuthResult;
import com.sanshao.bs.wxapi.alipay.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 支付类
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class PayUtils {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    public static final String WeiXinAppId = "";
    protected IWXAPI api;
    private OnPayListener mOnPayListener;

    public PayUtils() {
        initWechat();
    }

    public void setOnPayListener(OnPayListener onPayListener) {
        mOnPayListener = onPayListener;
    }

    /**
     * 初始化微信支付api
     */
    private void initWechat() {

        api = WXAPIFactory.createWXAPI(SSApplication.app, null, true);
        api.registerApp(WeiXinAppId);
    }

    /**
     * 调用微信支付
     */
    public void wxPay(Context activity, String orderInfo) {

        if (!CommandTools.isWeixinAvilible(activity)) {
            ToastUtil.showShortToast("请先安装微信客户端或选择其他方式支付");
            return;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(orderInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PayReq req = new PayReq();
        req.appId = jsonObject.optString("appid");
        req.partnerId = jsonObject.optString("partnerid");
        //预支付订单
        req.prepayId = jsonObject.optString("prepayid");
        req.nonceStr = jsonObject.optString("noncestr");
        req.timeStamp = jsonObject.optString("timestamp");
        req.packageValue = jsonObject.optString("package");
        req.sign = jsonObject.optString("sign");

        api.registerApp(req.appId);
//         在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//        3.调用微信支付sdk支付方法
        api.sendReq(req);
    }

    /**
     * 调用支付宝支付
     */
    public void aliPay(Activity activity, final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {

        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.showShortToast("支付成功");
                        if (mOnPayListener != null) {
                            mOnPayListener.onPaySuccess();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.showShortToast("支付失败");
                        if (mOnPayListener != null) {
                            mOnPayListener.onPayFailed();
                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.showShortToast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.showShortToast("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
