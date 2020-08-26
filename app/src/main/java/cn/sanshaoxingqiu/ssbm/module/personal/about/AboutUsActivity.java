package cn.sanshaoxingqiu.ssbm.module.personal.about;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityAboutUsBinding;

/**
 * 账户安全
 *
 * @Author yuexingxing
 * @time 2020/7/3
 */
public class AboutUsActivity extends BaseActivity<AboutUsViewModel, ActivityAboutUsBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutUsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData() {

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
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


}