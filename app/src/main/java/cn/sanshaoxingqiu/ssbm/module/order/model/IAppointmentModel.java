package cn.sanshaoxingqiu.ssbm.module.order.model;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.order.bean.AppointmentedInfo;

public interface IAppointmentModel {
    void returnAppointmentedList(List<AppointmentedInfo> appointmentedInfoList);
}
