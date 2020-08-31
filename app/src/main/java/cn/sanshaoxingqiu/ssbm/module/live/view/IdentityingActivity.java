package cn.sanshaoxingqiu.ssbm.module.live.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityIdentityingBinding;

/**
 * 主播认证审核中
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class IdentityingActivity extends BaseActivity<BaseViewModel, ActivityIdentityingBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, IdentityingActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData() {

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }

            @Override
            public void onTitleClick(View view) {

            }

            @Override
            public void onRightClick(View view) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identitying;
    }
}