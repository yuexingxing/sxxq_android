package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * @Author yuexingxing
 * @time 2020/7/8
 */
public class PaySuccessDialog {

    public PaySuccessDialog() {

    }

    public void show(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_layout_pay_success, null);
        ImageView ivClose = rootView.findViewById(R.id.iv_close);

        Dialog dialog = new Dialog(context, R.style.dialogSupply);
        dialog.setContentView(rootView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        setPosition(dialog);

        ivClose.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
    }

    public void setPosition(Dialog dialog) {

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
//        lp.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 0.7); // 宽度
//        lp.height = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getHeight() * 0.5); // 高度

        lp.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 0.7); // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }
}
