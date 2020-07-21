package com.sanshao.bs.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.exam.commonbiz.util.CommonCallBack;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

/**
 * Author     wildma
 * DATE       2017/07/16
 * Des	      ${友盟分享工具类}
 */
public class ShareUtils {

    public static String url = "https://mobile.umeng.com/";
    public static String text = "在这里和3千万有梦想的人一道实现大学梦!";
    public static String title = "轻松考大学，自考用布克";
    public static String imageurl = "";
    public static String videourl = "http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html";
    public static String musicurl = "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3";

    public static final int SHARE_CANCELED = -1;
    public static final int SHARE_FAILED = 0;
    public static final int SHARE_SUCCESS = 1;

    /**
     * 分享链接
     */
    public static void shareWeb(final Activity activity, String WebUrl, String title,
                                String description, String imageUrl, int imageID, SHARE_MEDIA platform,
                                CommonCallBack callBack) {

        if (!checkClient(activity, platform)) {
            return;
        }

        UMWeb web = new UMWeb(WebUrl);//连接地址
        web.setTitle(title);//标题
        web.setDescription(description);//描述
        if (TextUtils.isEmpty(imageUrl)) {
            web.setThumb(new UMImage(activity, imageID));  //本地缩略图
        } else {
            web.setThumb(new UMImage(activity, imageUrl));  //网络缩略图
        }
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (share_media.name().equals("WEIXIN_FAVORITE")) {
                                    Toast.makeText(activity, share_media + " 收藏成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
                                    if (callBack != null) {
                                        callBack.callback(SHARE_SUCCESS, null);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                        if (throwable != null) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
                                if (callBack != null) {
                                    callBack.callback(SHARE_FAILED, null);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "分享取消", Toast.LENGTH_SHORT).show();
                                if (callBack != null) {
                                    callBack.callback(SHARE_CANCELED, null);
                                }
                            }
                        });
                    }
                })
                .share();

        //新浪微博中图文+链接
        /*new ShareAction(activity)
                .setPlatform(platform)
                .withText(description + " " + WebUrl)
                .withMedia(new UMImage(activity,imageID))
                .share();*/
    }

    /**
     * 分享图片
     */
    public static void sharePhoto(final Activity activity, Bitmap bitmap, SHARE_MEDIA platform) {

        if (!checkClient(activity, platform)) {
            return;
        }

        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(new UMImage(activity, bitmap))
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (bitmap != null && !bitmap.isRecycled()) {
                                    bitmap.recycle();
                                }
                                if (share_media.name().equals("WEIXIN_FAVORITE")) {
                                    Toast.makeText(activity, share_media + " 收藏成功", Toast.LENGTH_SHORT).show();
                                } else {
//                                    Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                        if (throwable != null) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "分享取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .share();
    }

    public static boolean checkClient(Context context, SHARE_MEDIA platform) {

        if (SHARE_MEDIA.WEIXIN.equals(platform) || SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform)) {

            if (!isWeixinAvilible(context)) {
                ToastUtil.showShortToast("您还没有安装微信，请先安装微信客户端");
                return false;
            }
        }
        return true;
    }

    /**
     * 分享文字
     */
    public static void shareText(final Activity activity, String title,
                              SHARE_MEDIA platform,
                                 CommonCallBack callBack) {

        if (!checkClient(activity, platform)) {
            return;
        }

        new ShareAction(activity)
                .setPlatform(platform)
                .withText(title)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (share_media.name().equals("WEIXIN_FAVORITE")) {
                                    Toast.makeText(activity, share_media + " 收藏成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
                                    if (callBack != null) {
                                        callBack.callback(SHARE_SUCCESS, null);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                        if (throwable != null) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
                                if (callBack != null) {
                                    callBack.callback(SHARE_FAILED, null);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "分享取消", Toast.LENGTH_SHORT).show();
                                if (callBack != null) {
                                    callBack.callback(SHARE_CANCELED, null);
                                }
                            }
                        });
                    }
                })
                .share();

        //新浪微博中图文+链接
        /*new ShareAction(activity)
                .setPlatform(platform)
                .withText(description + " " + WebUrl)
                .withMedia(new UMImage(activity,imageID))
                .share();*/
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


}
