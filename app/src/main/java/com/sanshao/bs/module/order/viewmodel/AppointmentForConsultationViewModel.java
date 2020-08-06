package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.AppointmentedInfo;
import com.sanshao.bs.module.order.model.AppointMentModel;
import com.sanshao.bs.module.order.model.IAppointmentModel;

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

        AppointMentModel.getAppointmentedList(new OnLoadListener<AppointmentedInfo>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<AppointmentedInfo> t) {
                if (mCallBack != null) {
                    mCallBack.returnAppointmentedList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
