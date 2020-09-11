package com.sanshao.livemodule.liveroom.model;

import com.sanshao.livemodule.liveroom.roomutil.bean.GetRoomIdResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;

public interface ILiveRoomModel {

    void returnGetLicense(LicenceInfo licenceInfo);

    void returnUserSign(UserSignResponse userSignResponse);

    void returnGetBackVideo(GetRoomIdResponse getRoomIdResponse);

    void returnUploadLiveRoomInfo();
}
