package cn.sanshaoxingqiu.ssbm.module;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.exam.commonbiz.base.BaseActivity;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityTestMenuBinding;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import com.sanshao.commonui.pickerview.builder.OrderTimePickerBuilder;
import com.sanshao.commonui.pickerview.contrarywind.view.WheelView;
import com.sanshao.commonui.pickerview.listener.OnTimeSelectChangeListener;
import com.sanshao.commonui.pickerview.listener.OnTimeSelectListener;
import com.sanshao.commonui.pickerview.view.OrderTimePickerView;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.livemodule.zhibo.login.TCLoginActivity;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;
import cn.sanshaoxingqiu.ssbm.module.order.view.OrderListActivity;

import java.util.Date;

/**
 * 调试模块
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class TestMenuActivity extends BaseActivity<TestMenuViewModel, ActivityTestMenuBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestMenuActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_menu;
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

        binding.btnLogin.setOnClickListener(v -> LoginActivity.start(TestMenuActivity.this));
        binding.btnPay.setOnClickListener(v -> {

        });
        binding.btnLive.setOnClickListener(v -> {
            Intent intent = new Intent(context, TCLoginActivity.class);
            startActivity(intent);
        });
        binding.btnOrderList.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ALL));
        binding.btnOrderPicker.setOnClickListener(v -> showOrderPicker());
    }

    /**
     * 进账-日期选择弹窗
     */
    private void showOrderPicker() {
        OrderTimePickerView orderTimePickerView = new OrderTimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(context, CommandTools.getTime(date), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .setDividerType(WheelView.DividerType.FILL)
                .isAlphaGradient(true)
                .build();
        orderTimePickerView.setDialog();
        orderTimePickerView.show();
    }
}