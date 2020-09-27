package cn.sanshaoxingqiu.ssbm.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.order.bean.AppointmentedInfo;
import cn.sanshaoxingqiu.ssbm.module.order.model.AppointMentModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.IAppointmentModel;

/**
 * @Author yuexingxing
 * @time 2020/8/6
 */
public class AppointmentForConsultationViewModel extends BaseViewModel {
    private String TAG = AppointmentForConsultationViewModel.class.getSimpleName();

    private IAppointmentModel mCallBack;

    public void setCallBack(IAppointmentModel iAppointmentModel) {
        mCallBack = iAppointmentModel;
    }

    public void getAppointmentedList() {

        AppointMentModel.getAppointmentedList(new OnLoadListener<List<AppointmentedInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<List<AppointmentedInfo>> t) {
                if (mCallBack != null) {
                    mCallBack.returnAppointmentedList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (mCallBack != null) {
                    mCallBack.returnAppointmentedList(null);
                }
            }
        });
    }
}
