package com.sanshao.bs.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class FileUtil {

    /**
     * 创建新文件
     *
     * @return
     * @throws IOException
     */
    public static File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* 文件名 */
                    ".jpg",         /* 后缀 */
                    storageDir      /* 路径 */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
