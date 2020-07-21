package com.sanshao.bs.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.exam.commonbiz.config.CommonConfig;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, CommonConfig.weiXinAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

        Log.v(TAG, "微信支付结果: onReq" + req.getType());
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        int code = resp.errCode;

        Message msg = new Message();
        if (code == 0) {
            //显示充值成功的页面和需要的操作
            msg.what = 2001;
            EventBus.getDefault().post(msg);
        } else if (code == -1) {
            //错误
            msg.what = 2002;
            EventBus.getDefault().post(msg);
        } else if (code == -2) {
            //用户取消
            Toast.makeText(WXPayEntryActivity.this,"支付取消",Toast.LENGTH_LONG).show();
        }
        finish();
    }
}