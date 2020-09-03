package cn.sanshaoxingqiu.ssbm.module.common.oss;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;

import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/9/3
 */
public class OssViewModel extends ViewModel {
    private String TAG = OssViewModel.class.getSimpleName();
    private IOssModel mCallBack;

    public void setCallBack(IOssModel callBack) {
        mCallBack = callBack;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared");
    }

    public void uploadPic(int type, String filePath) {
        OssModel.uploadPic(filePath, new OnLoadListener<UploadPicResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<UploadPicResponse> t) {
                ToastUtil.showShortToast(t.getMsg());
                if (mCallBack != null) {
                    mCallBack.returnUploadPic(type, t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Log.d(TAG, "onLoadFailed-" + errMsg);
                ToastUtil.showShortToast(errMsg);
            }
        });
    }
}
