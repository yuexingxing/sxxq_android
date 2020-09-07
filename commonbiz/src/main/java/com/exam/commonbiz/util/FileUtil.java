package com.exam.commonbiz.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.exam.commonbiz.log.XLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具
 *
 * @Author yuexingxing
 * @time 2020/6/30
 */
public class FileUtil {

    public static final String FILE_PATH = "/sdcard/sanshao/";
    public static final String FILE_NAME = "idcard.png";

    private static final String TAG = "FileUtil";
    public static String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String FILE_DOWNLOAD_DIR = SD_CARD_PATH + "/sanshao" + "/download/";

    public static void initPath() {
        File f = new File(FILE_DOWNLOAD_DIR);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(String filePath, String fileName, Bitmap bitmap) {
        File f = new File(filePath, fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片到指定路径
     *
     * @param context
     * @param bitmap   要保存的图片
     * @param fileName 自定义图片名称  getString(R.string.app_name) + "" + System.currentTimeMillis()+".png"
     * @return true 成功 false失败
     */
    public static boolean saveImageToGallery(Context context, Bitmap bitmap, String fileName) {
        File rootFile = new File(FILE_DOWNLOAD_DIR);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File file = new File(rootFile, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片(80代表压缩20%)
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();

            //发送广播通知系统图库刷新数据
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            XLog.d("zdddz", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return false;
    }

}