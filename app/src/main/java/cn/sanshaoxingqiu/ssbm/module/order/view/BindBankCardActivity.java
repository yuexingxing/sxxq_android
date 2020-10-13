package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.commonui.pickerview.builder.OptionsPickerBuilder;
import com.sanshao.commonui.pickerview.contrarywind.view.WheelView;
import com.sanshao.commonui.pickerview.listener.OnOptionsSelectListener;
import com.sanshao.commonui.pickerview.view.OptionsPickerView;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.ArrayList;
import java.util.List;

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
    protected int getLayoutId() {
        return R.layout.activity_bind_bank_card;
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

        binding.ivSelBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initOptionPicker();
            }
        });
    }

    //Dialog 模式下，在底部弹出
    private void initOptionPicker() {
        final List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("中国建设银行");
        }
        OptionsPickerView mOptionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Toast.makeText(context, options1 + "-" + dataList.get(options1), Toast.LENGTH_LONG).show();
            }
        })
                .setTitleText("所属银行")
                .setSelectOptions(0)
                .setItemVisibleCount(5)
                .setDividerType(WheelView.DividerType.WRAP)
                .build();
        mOptionsPickerView.setPicker(dataList);
        mOptionsPickerView.setDialog();
        mOptionsPickerView.show();
    }

}