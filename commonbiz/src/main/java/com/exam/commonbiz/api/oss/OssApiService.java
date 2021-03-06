package com.exam.commonbiz.api.oss;

import com.exam.commonbiz.net.BaseResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Author yuexingxing
 * @time 2020/9/3
 */
public interface OssApiService {

    //公共图片上传
    @Multipart
    @POST("oss/publicUpload")
    Observable<BaseResponse<UploadPicResponse>> publicUpload(@Part MultipartBody.Part file);

    //图片上传
    @Multipart
    @POST("oss/upload")
    Observable<BaseResponse<UploadPicResponse>> uploadIdCard(@Part MultipartBody.Part file);
}
