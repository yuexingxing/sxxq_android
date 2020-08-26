package cn.sanshaoxingqiu.ssbm.module.personal.personaldata.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.Res;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;

/**
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class SelectSexDialog {

    private TextView mTvMale;
    private TextView mTvFeMale;
    private int sex;

    public void show(Context context, CommonCallBack commonCallBack) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_select_sex, null);
        ImageView ivClose = rootView.findViewById(R.id.iv_close);
        mTvMale = rootView.findViewById(R.id.tv_male);
        mTvFeMale = rootView.findViewById(R.id.tv_female);
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(rootView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

//        sex = SSApplication.getInstance().getUserInfo().sex;
        initView();

        rootView.findViewById(R.id.tv_female).setOnClickListener(v -> {
            sex = 0;
            initView();
        });
        rootView.findViewById(R.id.tv_male).setOnClickListener(v -> {
            sex = 1;
            initView();
        });
        ivClose.setOnClickListener(v -> dialog.dismiss());
        rootView.findViewById(R.id.btn_submit).setOnClickListener(v -> {
            dialog.dismiss();
            if (commonCallBack != null) {
                commonCallBack.callback(sex, null);
            }
        });
    }

    private void initView() {
        mTvMale.setTextSize(14);
        mTvFeMale.setTextSize(14);
        mTvMale.setTextColor(Res.getColor(SSApplication.app, R.color.color_999999));
        mTvFeMale.setTextColor(Res.getColor(SSApplication.app, R.color.color_999999));
        if (sex == 0) {
            mTvFeMale.setTextSize(16);
            mTvFeMale.setTextColor(Res.getColor(SSApplication.app, R.color.color_333333));
        } else {
            mTvMale.setTextSize(16);
            mTvMale.setTextColor(Res.getColor(SSApplication.app, R.color.color_333333));
        }
    }
}
