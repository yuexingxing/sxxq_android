package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
        helper.setText(R.id.tv_name, helper.getAdapterPosition() + "-" + item.name);
        CheckBox checkBox = helper.getView(R.id.checkbox);
        if (item.checked) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

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
