package com.exam.commonbiz.api.oss;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.util.BitmapUtil;
import com.exam.commonbiz.util.FileUtil;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/9/3
 */
public class OssViewModel extends BaseViewModel {
    private String TAG = OssViewModel.class.getSimpleName();
    private IOssModel mCallBack;

    public void setCallBack(IOssModel callBack) {
        mCallBack = callBack;
    }

    public void uploadPic(int type, Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap compressBitmap = BitmapUtil.compressBitmap(bitmap, 1024);
                FileUtil.saveBitmap(FileUtil.FILE_PATH, FileUtil.FILE_NAME, compressBitmap);

                Message message = new Message();
                message.obj = FileUtil.FILE_PATH + "/" + FileUtil.FILE_NAME;
                message.arg1 = type;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    public Handler mHandler = new Handler() {

        public void handleMessage(Message message) {
            int type = message.arg1;
            String filePath = (String) message.obj;
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
                    ToastUtil.showShortToast(errMsg);
                }
            });
        }
    };
}
