package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.ServiceTypeInfo;

/**
 * @Author yuexingxing
 * @time 2020/6/16
 */
public class ServiceTypeAdapter extends BaseQuickAdapter<ServiceTypeInfo, BaseViewHolder> {

    public ServiceTypeAdapter() {
        super(R.layout.item_layout_service_type, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceTypeInfo item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_content, item.content);
        if ((helper.getAdapterPosition() + 1) % 3 == 0) {
            helper.getView(R.id.view_line).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
        }
    }
}
