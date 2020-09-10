package com.sanshao.livemodule.zhibo;

import android.content.Context;
import android.text.TextUtils;

import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.GetRoomIdResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;
import com.tencent.rtmp.TXLiveBase;

/**
 * Module:   TCGlobalConfig
 * <p>
 * Function: 小直播 的全局配置类
 * <p>
 * 1. LiteAVSDK Licence
 * 2. 计算腾讯云 UserSig 的 SDKAppId、加密密钥、签名过期时间
 * 3. 小直播后台服务器地址
 * 4. App 主色调
 * 5. 是否启用连麦
 */

public class TCGlobalConfig {

    public static boolean isLicenseEmpty() {
        return TextUtils.isEmpty(LICENCE_URL);
    }

    public static boolean isUserSignEmpty() {
        return TextUtils.isEmpty(TCUserMgr.getInstance().getUserSign());
    }

    /**
     * 获取直播licence
     */
    public static void getLiveLicence(final Context context) {

        LiveViewModel liveViewModel = new LiveViewModel();
        liveViewModel.setILiveRoomModel(new ILiveRoomModel() {
            @Override
            public void returnGetLicense(LicenceInfo licenceInfo) {
                if (licenceInfo == null) {
                    return;
                }
                LICENCE_URL = licenceInfo.licenceUrl;
                LICENCE_KEY = licenceInfo.licenceKey;

                init(context);
            }

            @Override
            public void returnUserSign(UserSignResponse userSignResponse) {

            }

            @Override
            public void returnGetRoomId(GetRoomIdResponse getRoomIdResponse) {

            }

            @Override
            public void returnUploadLiveRoomInfo() {

            }
        });

        liveViewModel.getLicense();
    }

    /**
     * 获取用户签名
     */
    public static void getUserSign() {

        LiveViewModel liveViewModel = new LiveViewModel();
        liveViewModel.setILiveRoomModel(new ILiveRoomModel() {
            @Override
            public void returnGetLicense(LicenceInfo licenceInfo) {

            }

            @Override
            public void returnUserSign(UserSignResponse userSignResponse) {
                if (userSignResponse == null) {
                    return;
                }
                TCUserMgr.getInstance().setUserId(userSignResponse.userID);
                TCUserMgr.getInstance().setUserSign(userSignResponse.userSig);
                TCUserMgr.getInstance().setSdkAppId(userSignResponse.sdkAppId);
            }

            @Override
            public void returnGetRoomId(GetRoomIdResponse getRoomIdResponse) {

            }

            @Override
            public void returnUploadLiveRoomInfo() {

            }
        });

        liveViewModel.getUserSig();
    }

    public static void init(Context context) {

        // 必须：初始化 LiteAVSDK Licence。 用于直播推流鉴权。
        TXLiveBase.getInstance().setLicence(context, TCGlobalConfig.LICENCE_URL, TCGlobalConfig.LICENCE_KEY);

        // 必须：初始化 MLVB 组件
        MLVBLiveRoomImpl.sharedInstance(context);

        //必须：初始化全局的用户信息管理类 ，记录个人信息
        TCUserMgr.getInstance().initContext(context);
    }

    /**
     * 1. LiteAVSDK Licence。 用于直播推流鉴权。
     * <p>
     * 获取License，请参考官网指引 https://cloud.tencent.com/document/product/454/34750
     * test:http://license.vod2.myqcloud.com/license/v1/6c3b1373efc9e16d5b5b85cf29aae7a0/TXLiveSDK.licence
     * test:5cdaa5fc8611da1c9b4a28942042c704
     */
//    public static String LICENCE_URL = "http://license.vod2.myqcloud.com/license/v1/442a93c113745d2c786b12b0ea3ade73/TXLiveSDK.licence";
//    public static String LICENCE_KEY = "8671e4a00ab6bad66a635c5ad342ca60";
    public static String LICENCE_URL;
    public static String LICENCE_KEY;

    /**
     * 2.1 腾讯云 SDKAppId，需要替换为您自己账号下的 SDKAppId。
     * <p>
     * 进入腾讯云直播[控制台-直播SDK-应用管理](https://console.cloud.tencent.com/live/license/appmanage) 创建应用，即可看到 SDKAppId，
     * 它是腾讯云用于区分客户的唯一标识。
     * test:1400385196
     */
    public static final int SDKAPPID = 1400349402;

    /**
     * 2.2 计算签名用的加密密钥，获取步骤如下：
     * <p>
     * step1. 进入腾讯云直播[控制台-直播SDK-应用管理](https://console.cloud.tencent.com/live/license/appmanage)，如果还没有应用就创建一个，
     * step2. 单击您的应用，进入"应用管理"页面。
     * step3. 点击“查看密钥”按钮，就可以看到计算 UserSig 使用的加密的密钥了，请将其拷贝并复制到如下的变量中。
     * 如果提示"请先添加管理员才能生成公私钥"，点击"编辑"，输入管理员名称，如"admin"，点"确定"添加管理员。然后再查看密钥。
     * <p>
     * 注意：该方案仅适用于调试Demo，正式上线前请将 UserSig 计算代码和密钥迁移到您的后台服务器上，以避免加密密钥泄露导致的流量盗用。
     * 文档：https://cloud.tencent.com/document/product/647/17275#Server
     * test:d0032f7ba1d3e815e833cc781b35c2a3041e99d0fb6c4d20d5a3436c973c3529
     */
    public static final String SECRETKEY = "9c5201c79df65d03b4ea5ae2074a7c87a4b7c223a4a271fdc5464cf38402d51f";

    /**
     * 2.3 签名过期时间，建议不要设置的过短
     * <p>
     * 时间单位：秒
     * 默认时间：7 x 24 x 60 x 60 = 604800 = 7 天
     */
    public static final int EXPIRETIME = 604800;

    /**
     * 3. 小直播后台服务器地址
     * <p>
     * 3.1 您可以不填写后台服务器地址：
     * 小直播 App 单靠客户端源码运行，方便快速跑通体验小直播。
     * 不过在这种模式下运行的“小直播”，没有注册登录、回放列表等功能，仅有基本的直播推拉流、聊天室、连麦等功能。
     * 另外在这种模式下，腾讯云安全签名 UserSig 是使用本地 GenerateTestUserSig 模块计算的，存在 SECRETKEY 被破解的导致腾讯云流量被盗用的风险。
     * <p>
     * 3.2 您可以填写后台服务器地址：
     * 服务器需要您参考文档 https://cloud.tencent.com/document/product/454/15187 自行搭建。
     * 服务器提供注册登录、回放列表、计算 UserSig 等服务。
     * 这种情况下 {@link #SDKAPPID} 和 {@link #SECRETKEY} 可以设置为任意值。
     * <p>
     * 注意：
     * 后台服务器地址（APP_SVR_URL）和 （SDKAPPID，SECRETKEY）一定要填一项。
     * 要么填写后台服务器地址（@link #APP_SVR_URL），要么填写 {@link #SDKAPPID} 和 {@link #SECRETKEY}。
     * <p>
     * 详情请参考：
     * http://123.207.223.178
     */
    public static final String APP_SVR_URL = "";

    /**
     * 4. App 主色调。
     */
    public static final int MAIN_COLOR = 0xff222B48;


    /**
     * 5. 是否启用连麦。
     * <p>
     * 由于连麦功能使用了比较昂贵的 BGP 专用线路，所以是按照通话时长进行收费的。最初级的体验包包含 3000 分钟的连麦时长，只需要 9.8 元。
     * 购买链接：https://buy.cloud.tencent.com/mobilelive?urlctr=yes&micconn=3000m##
     */
    public static final boolean ENABLE_LINKMIC = false;
}
