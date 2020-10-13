package cn.sanshaoxingqiu.ssbm.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.ScreenUtil;
import com.exam.commonbiz.util.ToastUtil;
import com.google.gson.Gson;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import udesk.core.UdeskConst;

/**
 * @Author yuexingxing
 * @time 2020/6/29
 */
public class CommandTools {

    /**
     * 判断字符串是否符合手机号码格式
     *
     * @param phone
     * @return 待检测的字符串
     */
    public static boolean isMobileNum(String phone) {
        String telRegex = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        if (TextUtils.isEmpty(phone))
            return false;
        else
            return phone.matches(telRegex);
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
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
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    private static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 把文本复制到粘贴板
     *
     * @param context
     * @param content
     */
    public static void copyToClipboard(Context context, String content) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
        ToastUtil.showShortToast("复制成功");
    }

    /**
     * 拨打客服弹窗
     *
     * @param context
     */
    public static void showCall(Context context) {

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo(Constants.SERVICE_PHONE));
        commonDialogInfoList.add(new CommonDialogInfo("呼叫"));

        CommonBottomDialog commonBottomDialog = new CommonBottomDialog();
        commonBottomDialog.init(context)
                .setData(commonDialogInfoList)
                .autoDismissDialog(false)
                .setOnItemClickListener(commonDialogInfo -> {
                    if (commonDialogInfo.position == 1) {
                        commonBottomDialog.dismissDialog();
                        callPhone(context, Constants.SERVICE_PHONE);
                    }
                })
                .show();
    }

    /**
     * 判断当前view是否在屏幕可见
     */
    public static boolean getLocalVisibleRect(Context context, View view, int offsetY) {
        Point p = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(p);
        int screenWidth = p.x;
        int screenHeight = p.y;
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        location[1] = location[1] + ScreenUtil.dp2px(context, offsetY);
        view.getLocationInWindow(location);
        view.setTag(location[1]);//存储y方向的位置
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */

    public static String beanToJson(Object bean) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(bean);
        System.out.println(jsonStr);
        return jsonStr;
    }

    /**
     * @param uri     content:// 样式
     * @param context
     * @return real file path
     */
    public static String getFilePathFromContentUri(Uri uri, Context context) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    /**
     * 跳转客服
     */
    public static void startServiceChat() {

        String sdkToken = getSerialNumber();
        UserInfo userInfo = SSApplication.getUserInfo();
        if (userInfo != null && !TextUtils.isEmpty(userInfo.mem_id)) {
            sdkToken = userInfo.mem_id;
        }

        UdeskSDKManager.getInstance().entryChat(SSApplication.getInstance(), getUdeskConfig(userInfo), sdkToken);
    }

    /**
     * 配置用户信息
     *
     * @return
     */
    public static UdeskConfig getUdeskConfig(UserInfo userInfo) {

        if (userInfo == null || TextUtils.isEmpty(userInfo.mem_id)) {
            return UdeskConfig.createDefualt();
        }

        Map<String, String> info = new HashMap<>();
        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, userInfo.mem_id);
        //以下信息是可选
        info.put(UdeskConst.UdeskUserInfo.NICK_NAME, userInfo.nickname);
        info.put(UdeskConst.UdeskUserInfo.EMAIL, "");
        info.put(UdeskConst.UdeskUserInfo.CELLPHONE, userInfo.mem_phone);
        info.put(UdeskConst.UdeskUserInfo.DESCRIPTION, "");

        UdeskConfig.Builder builder = new UdeskConfig.Builder();
        builder.setDefualtUserInfo(info);
        return builder.build();
    }

    public static String getSerialNumber() {
        String SerialNumber = android.os.Build.SERIAL;
        return SerialNumber;
    }

    /**
     * 获取APP版本
     *
     * @return APP版本
     */
    public static String getAppVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        String version = "";
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
