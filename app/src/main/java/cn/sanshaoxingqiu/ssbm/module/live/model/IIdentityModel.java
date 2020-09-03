package cn.sanshaoxingqiu.ssbm.module.live.model;

import cn.sanshaoxingqiu.ssbm.module.live.bean.LiveApplyResponse;

public interface IIdentityModel {
    void returnLiveApply();
    void returnAnchorDetail(LiveApplyResponse liveApplyResponse);
}
