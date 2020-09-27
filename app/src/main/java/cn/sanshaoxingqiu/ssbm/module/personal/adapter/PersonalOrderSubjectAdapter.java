package cn.sanshaoxingqiu.ssbm.module.personal.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.order.bean.AppointmentedInfo;
import cn.sanshaoxingqiu.ssbm.widget.countdowntimer.CountdownView;

/**
 * @Author yuexingxing
 * @time 2020/7/14
 */
public class PersonalOrderSubjectAdapter extends BaseQuickAdapter<AppointmentedInfo, BaseViewHolder> {

    public PersonalOrderSubjectAdapter() {
        super(R.layout.item_layout_personal_order_subject, null);
    }

    private OnItemTimeEndListener mCallBack;

    public void setOnItemTimeEndListener(OnItemTimeEndListener onItemClickListener) {
        mCallBack = onItemClickListener;
    }

    public interface OnItemTimeEndListener {
        void onTimeEnd(AppointmentedInfo appointmentedInfo);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppointmentedInfo item) {
        helper.setText(R.id.tv_title, item.sarti_name);

        Log.d("zdddz", item.sarti_name + "/" + item.remainSeconds);
        CountdownView countdownView = helper.getView(R.id.tv_last_time);
        countdownView.start(item.remainSeconds * 1000);
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if (mCallBack != null) {
                    mCallBack.onTimeEnd(item);
                }
            }
        });

        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.view_space_top).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_space_top).setVisibility(View.GONE);
        }
    }
}
