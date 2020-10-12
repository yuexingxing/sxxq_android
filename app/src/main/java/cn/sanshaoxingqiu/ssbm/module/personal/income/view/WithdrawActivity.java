package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.base.EmptyWebViewActivity;
import com.exam.commonbiz.util.Constants;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityWithdrawBinding;

/**
 * 提现界面
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class WithdrawActivity extends BaseActivity<BaseViewModel, ActivityWithdrawBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, WithdrawActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
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
        //添加银行卡
        binding.llAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //提现协议
        binding.tvWithdrawPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmptyWebViewActivity.start(context, "", Constants.withdrawalrulesUrl);
            }
        });
        //开始提现
        binding.tvWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //全部提现
        binding.tvWithdrawAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}