package cn.sanshaoxingqiu.ssbm.module.common.oss;

import com.exam.commonbiz.net.BaseResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @Author yuexingxing
 * @time 2020/9/3
 */
public interface OssApiService {

    //图片上传
    @POST("api/oss/upload")
    Observable<BaseResponse<UploadPicResponse>> uploadPic(@Body MultipartBody.Part file);
}
