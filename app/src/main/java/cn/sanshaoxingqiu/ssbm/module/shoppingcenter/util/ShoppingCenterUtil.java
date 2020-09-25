package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.util;

import com.exam.commonbiz.util.Constants;

import cn.sanshaoxingqiu.ssbm.SSApplication;

public class ShoppingCenterUtil {

    public static String getRegisterTagId() {
        if (SSApplication.getInstance().isProEnvironment()) {
            return Constants.TAG_ID_REGISTER_PRO;
        } else {
            return Constants.TAG_ID_REGISTER_DEV;
        }
    }

    public static String getInviteTagId() {
        if (SSApplication.getInstance().isProEnvironment()) {
            return Constants.TAG_ID_INVITE_PRO;
        } else {
            return Constants.TAG_ID_INVITE_DEV;
        }
    }
}
