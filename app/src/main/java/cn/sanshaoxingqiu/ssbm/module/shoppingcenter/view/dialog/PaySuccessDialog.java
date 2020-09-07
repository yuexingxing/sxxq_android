package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import com.exam.commonbiz.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/8
 */
public class PaySuccessDialog {

    public PaySuccessDialog() {

    }

    public void show(Context context, GoodsDetailInfo goodsDetailInfo) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_layout_pay_success, null);
        ImageView ivClose = rootView.findViewById(R.id.iv_close);
        ImageView ivIcon = rootView.findViewById(R.id.iv_icon);

        Dialog dialog = new Dialog(context, R.style.dialogSupply);
        dialog.setContentView(rootView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        setPosition(dialog);

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        if (!TextUtils.isEmpty(userInfo.mem_class.mem_class_key)) {
            //比较星级，选择最大的
            if (!TextUtils.isEmpty(userInfo.mem_class.mem_class_key) && userInfo.mem_class.mem_class_key.compareTo(goodsDetailInfo.mem_class_key) > 0) {
                goodsDetailInfo.mem_class_key = userInfo.mem_class.mem_class_key;
            }
        }

        if (goodsDetailInfo.isOneStarMember()) {
            ivIcon.setImageResource(R.drawable.image_onestarpaymentissuccessful);
        } else if (goodsDetailInfo.isTwoStarMember()) {
            ivIcon.setImageResource(R.drawable.image_twostarpaymentissuccessful);
        } else if (goodsDetailInfo.isThreeStarMember()) {
            ivIcon.setImageResource(R.drawable.image_threestarpaymentissuccessful);
        }

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
