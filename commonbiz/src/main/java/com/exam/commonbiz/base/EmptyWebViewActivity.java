package com.exam.commonbiz.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.exam.commonbiz.R;
import com.exam.commonbiz.util.Constants;

/**
 * 通用H5页面
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class EmptyWebViewActivity extends BaseWebViewActivity {

    public static void start(Context context, String title, String url) {
        Intent starter = new Intent(context, EmptyWebViewActivity.class);
        starter.putExtra(Constants.OPT_DATA, title);
        starter.putExtra(Constants.OPT_DATA2, url);
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
        String title = getIntent().getStringExtra(Constants.OPT_DATA);
        String url = getIntent().getStringExtra(Constants.OPT_DATA2);
//        mTitleBar.setTitle(title);
        if (!TextUtils.isEmpty(url)) {
            initWebView(url);
        }
    }
}