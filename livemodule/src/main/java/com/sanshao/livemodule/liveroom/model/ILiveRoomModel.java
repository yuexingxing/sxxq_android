package com.sanshao.livemodule.liveroom.model;

import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;

public interface ILiveRoomModel {

    void returnUserSign(UserSignResponse userSignResponse);

    void returnGetVideoList(VideoListResponse videoListResponse);
}
