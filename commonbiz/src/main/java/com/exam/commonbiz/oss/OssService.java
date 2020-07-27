package com.exam.commonbiz.oss;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class OssService {
    private OSS oss;
    private String bucket;
    private PicResultCallback mPicResultCallback;//回调接口
    private String path = "";//地址(后台告诉);
    private static TokenBean tokenBean;

    public static OssService initOSS(Context context, String endpoint, String bucket) {
        OSSCredentialProvider credentialProvider;
        credentialProvider = new STSGetter(tokenBean);
        //设置网络参数
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);
        return new OssService(oss, bucket);
    }

    public void setCallback(PicResultCallback callback) {
        mPicResultCallback = callback;
    }

    public OssService(OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;
    }


    public void asyncPutImage(String object, final String localFile, final ProgressBar mProgress, String type) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }
        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);
        put.setCallbackParam(new HashMap<String, String>() {


            {
                put("callbackUrl", path);

                put("callbackBody", "filename=${object}&size=${size}&id=${x:id}&action=${x:action}");
                //https://help.aliyun.com/document_detail/31989.html?spm=5176.doc31984.6.883.brskVg
            }

        });
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("x:id", "id");
        hashMap.put("x:action", type);
        put.setCallbackVars(hashMap);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override


            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                int progress = (int) (100 * currentSize / totalSize);
                if (mProgress != null) {
                    mProgress.setProgress(progress);
                }
            }

        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override

            public void onSuccess(PutObjectRequest request, final PutObjectResult result) {
                Observable.just(result).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PutObjectResult>() {
                    @Override
                    public void accept(PutObjectResult putObjectResult) throws Exception {
                        if (mProgress != null) {
                            mProgress.setVisibility(View.GONE);
                        }
                        if (mPicResultCallback != null) {
                            mPicResultCallback.getOSSPicData(result, localFile);
                        }
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
            }
        });
    }

    //成功的回调接口
    public interface PicResultCallback {
        void getOSSPicData(PutObjectResult data, String oldPath);
    }
}
