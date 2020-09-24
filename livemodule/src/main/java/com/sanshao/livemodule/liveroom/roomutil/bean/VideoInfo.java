package com.sanshao.livemodule.liveroom.roomutil.bean;

import android.text.TextUtils;

import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

public class VideoInfo extends TCVideoInfo {

    public String live_batch_id;
    public String live_title;
    public String like_number = "0";
    public String live_status;
    public String pic_url;
    public String rtmp_pull_url;
    public String flv_pull_url;
    public String hls_pull_url;
    public int viewer_count = 0;
    public String frontcover;
    public AnchorInfo pushers;
    public String room_id;
    public String meta_type;//1 直播 2回放 3短视频
    public String live_start_time;

    /**
     * 是不是直播
     * @return
     */
    public boolean isLive(){
        return TextUtils.equals("1", meta_type);
    }
}
