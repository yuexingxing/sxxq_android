package com.sanshao.livemodule.liveroom.viewmodel;

import android.widget.Toast;

import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.model.LiveModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.GetRoomIdResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UploadRoomInfoRequest;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;

public class LiveViewModel {

    private ILiveRoomModel mILiveRoomModel;

    public void getLicense() {

        LiveModel.getLicense(new OnLoadListener<LicenceInfo>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<LicenceInfo> t) {
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnGetLicense(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

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

    public void getRoomId() {

        LiveModel.getRoomId(new OnLoadListener<GetRoomIdResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<GetRoomIdResponse> t) {
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnGetRoomId(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadLiveRoomInfo(UploadRoomInfoRequest uploadRoomInfoRequest) {

        LiveModel.uploadLiveRoomInfo(uploadRoomInfoRequest, new OnLoadListener() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                if (mILiveRoomModel != null) {
                    mILiveRoomModel.returnUploadLiveRoomInfo();
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}