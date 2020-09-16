package com.sanshao.livemodule.liveroom.viewmodel;

import android.widget.Toast;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.model.LiveModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;

public class LiveViewModel extends BaseViewModel {

    private ILiveRoomModel mILiveRoomModel;

    public void setILiveRoomModel(ILiveRoomModel iLiveRoomModel) {
        mILiveRoomModel = iLiveRoomModel;
    }

    public void getUserSig() {

        LiveModel.getUserSig(new OnLoadListener<UserSignResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<UserSignResponse> t) {
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnUserSign(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getVideoList(int page, int pageSize) {

        LiveModel.getVideoList(page, pageSize, new OnLoadListener<VideoListResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<VideoListResponse> t) {
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnGetVideoList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnGetVideoList(null);
                }
            }
        });
    }

    public void getVideoBackList(int page, int pageSize) {

        LiveModel.getVideoBackList(page, pageSize, new OnLoadListener<VideoListResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<VideoListResponse> t) {
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnGetVideoList(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnGetVideoList(null);
                }
            }
        });
    }
}
