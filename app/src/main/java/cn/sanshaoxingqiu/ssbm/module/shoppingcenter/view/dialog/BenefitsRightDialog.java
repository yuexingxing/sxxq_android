package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.exam.commonbiz.util.CommonCallBack;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * 商品推荐弹窗
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class BenefitsRightDialog {

    public void show(Context context, CommonCallBack commonCallBack) {
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_benefits_right, null);
        Dialog dialog = new Dialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(layout);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        layout.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (commonCallBack != null) {
                    commonCallBack.callback(0, null);
                }
            }
        });
    }
}
