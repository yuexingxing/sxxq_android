package cn.sancell.ssbm.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.ToastUtil;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

        try {
            if (api != null && !api.handleIntent(getIntent(), this)) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
    }

    public void initData() {
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        api.registerApp(Constants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onShowMessageFromWXReq:zoulereq ");
    }

    @Override
    public void onResp(BaseResp baseResp) {

        if (baseResp == null) {
            Log.d(TAG, "onShowMessageFromWXReq:zouleresq----baseResp == null");
            return;
        }

        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData = launchMiniProResp.extMsg;
            ToastUtil.showShortToast("onResp-小程序返回");
            finish();
        }

        Log.d(TAG, "openId----" + baseResp.openId);
        Log.d(TAG, "getType----" + baseResp.getType());
        if (baseResp.getType() == ConstantsAPI.COMMAND_SUBSCRIBE_MESSAGE) {

            if (!TextUtils.isEmpty(baseResp.openId)) {
                Message message = new Message();
                message.what = ConstantsAPI.COMMAND_SUBSCRIBE_MESSAGE;
                message.obj = baseResp.openId;
                EventBus.getDefault().post(message);
            }
        }
        finish();
    }
}
