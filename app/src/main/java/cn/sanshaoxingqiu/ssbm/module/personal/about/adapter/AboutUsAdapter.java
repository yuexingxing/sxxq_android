package cn.sanshaoxingqiu.ssbm.module.personal.about.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.about.AgreementInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.GoodsListAdapter;

/**
 * @Author yuexingxing
 * @time 2020/7/21
 */
public class AboutUsAdapter extends BaseQuickAdapter<AgreementInfo, BaseViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public AboutUsAdapter() {
        super(R.layout.item_layout_about_us, null);
    }

    public interface OnItemClickListener {
        void onItemClick(AgreementInfo agreementInfo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, AgreementInfo item) {
        helper.setText(R.id.tv_title, item.title);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(item);
                }
            }
        });
    }
}
