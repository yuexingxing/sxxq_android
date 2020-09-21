package cn.sanshaoxingqiu.ssbm.module;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.exam.commonbiz.base.BaseWebViewActivity;
import com.exam.commonbiz.util.ToastUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * 通用H5页面
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class EmptyWebViewActivity extends BaseWebViewActivity {

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, EmptyWebViewActivity.class);
        starter.putExtra("url", url);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty_web_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        String url = getIntent().getStringExtra("url");
        initWebView(url);

        getWebView().loadUrl("file:///android_asset/ExampleApp.html");
        mWebView.registerHandler("shareProductFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                String str ="这是html返回给java的数据:" + data;

                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                ToastUtil.showShortToast("app_share_product" + data);
                function.onCallBack( str + ",Java经过处理后截取了一部分："+ str.substring(0,5));
            }
        });
    }
}