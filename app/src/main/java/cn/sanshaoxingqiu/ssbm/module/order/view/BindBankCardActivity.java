package cn.sanshaoxingqiu.ssbm.module.order.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityBindBankCardBinding;

/**
 * 绑定银行卡
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class BindBankCardActivity extends BaseActivity<BaseViewModel, ActivityBindBankCardBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, BindBankCardActivity.class);
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
        return R.layout.activity_bind_bank_card;
    }
}