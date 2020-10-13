package cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;

/**
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class BankCardAdapter extends BaseQuickAdapter<BankCardInfo, BaseViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, BankCardInfo bankCardInfo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BankCardAdapter() {
        super(R.layout.item_layout_bank_card, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardInfo item) {
        helper.setText(R.id.tv_name, item.bank_name);
        helper.setText(R.id.tv_bank_no, item.bank_card_number);

        CheckBox checkBox = helper.getView(R.id.checkbox);
        if (item.checked) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        GlideUtil.loadImage(item.bank_card_icon, helper.getView(R.id.iv_bank_icon));

        RoundedImageView roundedImageView = helper.getView(R.id.iv_bank_bg);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);//形状
        gradientDrawable.setCornerRadius(ScreenUtil.dp2px(helper.itemView.getContext(), 10));//设置圆角Radius
        gradientDrawable.setColor(Color.parseColor(item.bank_color));
        roundedImageView.setBackground(gradientDrawable);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(helper.getAdapterPosition(), item);
                }
            }
        });
    }
}
