package com.exam.commonbiz.base;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.exam.commonbiz.R;
import com.exam.commonbiz.bean.InitTokenInfo;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.bean.WebViewBaseInfo;
import com.exam.commonbiz.util.BeanUtil;
import com.exam.commonbiz.util.JavaScriptInterface;
import com.exam.commonbiz.util.MyHandlerCallBack;
import com.exam.commonbiz.util.StatusBarUtil;
import com.exam.commonbiz.util.ToastUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonui.titlebar.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @Author yuexingxing
 * @time 2020/6/29
 */
public abstract class BaseWebViewActivity extends AppCompatActivity {

    public final String TAG = BaseWebViewActivity.class.getSimpleName();
    private TitleBar mTitleBar;
    public BridgeWebView mWebView;
    private ProgressBar mProgressBar;
    private LinearLayout layoutBody;
    protected View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web_view);
        mTitleBar = findViewById(R.id.title_bar);
        mWebView = findViewById(R.id.webview_base);
        mProgressBar = findViewById(R.id.webview_progressbar_base);
        layoutBody = findViewById(R.id.ll_body);
        if (getLayoutId() != 0) {
            setContentViewId(getLayoutId());
        }
        initView();
        initData();
        setStatusBar();
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //重新开始webview，这样做的目的是为了不让webview重复进入的时候出现无法加载url出现空白
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode()) {
                StatusBarUtil.transparencyBar(this);
            } else {
                StatusBarUtil.setStatusBarColor(this, getStatusBarColor());
            }

            if (isUseBlackFontWithStatusBar()) {
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode());
            }
        }
    }

    public void initToken() {

        if (mWebView == null) {
            return;
        }

        UserInfo userInfo = BasicApplication.getUserInfo();
        WebViewBaseInfo<InitTokenInfo> webViewBaseInfo = new WebViewBaseInfo();
        webViewBaseInfo.errorCode = 0;
        webViewBaseInfo.message = "initToken";
        InitTokenInfo initTokenInfo = new InitTokenInfo();
        initTokenInfo.userId = userInfo.mem_id;
        initTokenInfo.invatationCode = userInfo.invitation_code;
        initTokenInfo.token = BasicApplication.getToken();
        webViewBaseInfo.result = initTokenInfo;

        mWebView.callHandler("initToken", new Gson().toJson(webViewBaseInfo), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d(TAG, "data123---: " + data);
            }
        });
    }

    /**
     * 是否设置成透明状态栏，即就是全屏模式
     */
    protected boolean isUseFullScreenMode() {
        return false;
    }

    /**
     * 更改状态栏颜色，只有非全屏模式下有效
     */
    public int getStatusBarColor() {
        return R.color.white;
    }

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     */
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }


    public void setContentViewId(int layoutId) {
        contentView = getLayoutInflater().inflate(layoutId, null);
        if (layoutBody.getChildCount() > 0) {
            layoutBody.removeAllViews();
        }
        if (contentView != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutBody.addView(contentView, params);
        }
    }

    /**
     * 当前页面暂停后
     */
    @Override
    public void onPause() {
        //如果当前web服务不是null
        if (webChromeClient != null) {
            //通知app当前页面要隐藏它的自定义视图。
            webChromeClient.onHideCustomView();
        }
        //让webview重新加载，用于停掉音视频的声音
        mWebView.reload();
        //先重载webview再暂停webview，这时候才真正能够停掉音视频的声音，api 2.3.3 以上才能暂停
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.onPause(); // 暂停网页中正在播放的视频
        }
        super.onPause();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @JavascriptInterface
    public void initWebView(String url) {
        initSetting();
        mWebView.loadUrl(url);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @JavascriptInterface
    public void initWebView(String url, Map<String, String> map) {
        initSetting();
        mWebView.loadUrl(url, map);
    }

    public void initSetting() {
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.addJavascriptInterface(new JavaScriptInterface(this), "app");//JS交互
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setDefaultHandler(new MyHandlerCallBack());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setDomStorageEnabled(true);//设置适应Html5
        webSettings.setAppCacheEnabled(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 设置允许JS弹窗
        webSettings.setDefaultTextEncodingName("UTF-8");
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        // 自适应 屏幕大小界面
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setUserAgentString("BookEdu Client/Android V");

        //加载https处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //当进度走到100的时候做自己的操作，我这边是弹出dialog
                if (mProgressBar != null && mWebView != null) {
                    mProgressBar.setProgress(progress);
                    if (progress == 100) {
                        mProgressBar.setVisibility(View.GONE);
                        mWebView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }

    /**
     * webview客户端
     * 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
     */
    private class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                //在这里你可以拦截url，然后自己处理一些事情，比如跳转app内部网页

                Log.v("webview", url);
                view.loadUrl(url);
                return true;
            } catch (Exception e) {
                Log.i("webview", "该链接无效");
                return true;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            // 重新测量
            view.measure(w, h);
            super.onPageFinished(view, url);
            if (mTitleBar != null) {
                mTitleBar.setTitle(view.getTitle());
            }
            initToken();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView mWebView, String url, String message, JsResult result) {
//            AlertDialog.Builder localBuilder = new AlertDialog.Builder(mWebView.getContext());
//            localBuilder.setMessage(message).setPositiveButton("确定", null);
//            localBuilder.setCancelable(false);
//            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (mProgressBar != null) {
                mProgressBar.setProgress(newProgress);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mWebView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            mWebView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        } else if (mWebView.canGoBack()) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 该抽象方法就是 onCreate中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据，子类可不复写 执行数据的加载
     */
    protected void initData() {
    }

    public void destroyWebView() {
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            mWebView.freeMemory();
            mWebView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }
}