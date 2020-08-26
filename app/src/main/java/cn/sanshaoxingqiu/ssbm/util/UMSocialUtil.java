package cn.sanshaoxingqiu.ssbm.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.exam.commonbiz.util.CommonCallBack;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.module.login.bean.AuthInfo;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StatusCode;

import java.util.Map;

/**
 * yxx
 * <p>
 * ${Date} ${time}
 **/
public class UMSocialUtil {

    //微信授权
    public static void authorization(Context context, SHARE_MEDIA share_media, CommonCallBack callBack) {

        boolean flag = ShareUtils.isWeixinAvilible(context);
        if (!flag) {
            ToastUtil.showShortToast("请先安装微信客户端");
            return;
        }

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI umShareAPI = UMShareAPI.get(context);
        umShareAPI.setShareConfig(config);
        Activity activity = (Activity) context;
        umShareAPI.getPlatformInfo(activity, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d("userinfo", "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d("userinfo", "onComplete " + "授权完成");

                Log.v("userinfo", map.toString());
                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                if (TextUtils.isEmpty(gender)) {
                    gender = "0";
                } else if (gender.equals("男")) {
                    gender = "1";
                } else {
                    gender = "2";
                }

                AuthInfo authInfo = new AuthInfo();
                authInfo.setUid(uid);
                authInfo.setOpenid(openid);
                authInfo.setUnionid(unionid);
                authInfo.setName(name);
                authInfo.setGender(gender);
                authInfo.setIconurl(iconurl);
                callBack.callback(0, authInfo);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d("userinfo", "onError " + "授权失败");
                ToastUtil.showShortToast("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d("userinfo", "onCancel " + "授权取消");
                ToastUtil.showShortToast("授权取消");
            }
        });
    }

    /**
     * 注销本次登陆
     */
    public static void logoutAuth(Context context, SHARE_MEDIA share_media, CommonCallBack callBack) {

        Activity activity = (Activity) context;
        UMShareAPI.get(SSApplication.app).deleteOauth(activity, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                String showText = "解除" + share_media.toString() + "平台授权成功";
                if (i != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + share_media.toString() + "平台授权失败[" + i + "]";
                }
                Toast.makeText(context, showText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
}
