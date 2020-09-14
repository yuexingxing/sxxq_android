package com.sanshao.livemodule.liveroom.model;

import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

import java.util.List;

public interface ILiveRoomModel {

    void returnGetLicense(LicenceInfo licenceInfo);

    void returnUserSign(UserSignResponse userSignResponse);

    void returnGetVideoList(List<TCVideoInfo> tcVideoInfoList);
}
