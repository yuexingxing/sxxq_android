package cn.sanshaoxingqiu.ssbm.module;

import android.content.Context;
import android.content.Intent;

import com.exam.commonbiz.base.BaseWebViewActivity;

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
    }
}