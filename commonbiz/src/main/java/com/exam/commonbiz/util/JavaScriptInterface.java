package com.exam.commonbiz.util;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * 安卓JS交互类
 * yxx
 * <p>
 * ${Date} ${time}
 **/
public class JavaScriptInterface {

    private Context context;

    public JavaScriptInterface(Context c) {
        this.context = c;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     * {"type":"Unxxxt","id":1,"title":"测试分享的标题","content":"测试分享的内容","url":"http://www.baidu.com"}
     */
    @JavascriptInterface
    public void remixJump(String type) {

    }

    @JavascriptInterface
    public void subscribe() {

    }

    @JavascriptInterface
    public void goback() {

    }

}