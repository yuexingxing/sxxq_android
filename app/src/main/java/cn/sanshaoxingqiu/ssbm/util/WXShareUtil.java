package cn.sanshaoxingqiu.ssbm.util;

import android.app.Activity;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 微信分享
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class WXShareUtil {

    public static void shareText(Activity context, String content) {
        new ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .withText(content)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }

    public static void sharePicture(Activity context, Bitmap bitmap) {
        UMImage umImage = new UMImage(context, bitmap);
        new ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .withExtra(umImage)
                .setCallback(shareListener)
                .share();
    }

    private static UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showShortToast("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showShortToast("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showShortToast("取消分享");
        }
    };
}
