package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * 商品服务保障弹窗
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class GoodsServiceDialog {

    public void show(Context context) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_goods_service, null);
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(rootView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }
}
