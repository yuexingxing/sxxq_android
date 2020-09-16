package com.sanshao.livemodule.liveroom.viewmodel;

import android.widget.Toast;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.base.IBaseModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.model.LiveModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoListResponse;

public class LiveViewModel extends BaseViewModel {

    private ILiveRoomModel mILiveRoomModel;
    private IBaseModel mIBaseModel;

    public void setILiveRoomModel(ILiveRoomModel iLiveRoomModel) {
        mILiveRoomModel = iLiveRoomModel;
    }

    public void setIBaseModel(IBaseModel iBaseModel){
        mIBaseModel = iBaseModel;
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

    public void getVideoList(final int page, int pageSize) {

        LiveModel.getVideoList(page, pageSize, new OnLoadListener<VideoListResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<VideoListResponse> t) {
                if (mIBaseModel != null) {
                    if (page == 1) {
                        mIBaseModel.onRefreshData(t.getContent());
                    } else {
                        mIBaseModel.onLoadMoreData(t.getContent());
                    }
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
                if (mIBaseModel != null) {
                    mIBaseModel.onRefreshData(null);
                }
            }
        });
    }

    public void getVideoBackList(final int page, int pageSize) {

        LiveModel.getVideoBackList(page, pageSize, new OnLoadListener<VideoListResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<VideoListResponse> t) {
                if (mIBaseModel != null) {
                    if (page == 1) {
                        mIBaseModel.onRefreshData(t.getContent());
                    } else {
                        mIBaseModel.onLoadMoreData(t.getContent());
                    }
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Toast.makeText(BasicApplication.app, errMsg, Toast.LENGTH_SHORT).show();
                if (mIBaseModel != null) {
                    mIBaseModel.onRefreshData(null);
                }
            }
        });
    }
}
