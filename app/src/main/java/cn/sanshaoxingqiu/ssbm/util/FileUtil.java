package cn.sanshaoxingqiu.ssbm.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class FileUtil {

    public static final String FILE_PATH = "/sdcard/sanshao/";
    public static final String FILE_NAME = "idcard.png";

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

    /**
     * 压缩图片到目标大小以下
     *
     * @param file
     * @param targetSize
     */
    public static void compressBmpFileToTargetSize(File file, long targetSize) {
        if (file.length() > targetSize) {
            // 每次宽高各缩小一半
            int ratio = 2;
            // 获取图片原始宽高
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            int targetWidth = options.outWidth / ratio;
            int targetHeight = options.outHeight / ratio;

            // 压缩图片到对应尺寸
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int quality = 100;
            Bitmap result = generateScaledBmp(bitmap, targetWidth, targetHeight, baos, quality);

            // 计数保护，防止次数太多太耗时。
            int count = 0;
            while (baos.size() > targetSize && count <= 10) {
                targetWidth /= ratio;
                targetHeight /= ratio;
                count++;

                // 重置，不然会累加
                baos.reset();
                result = generateScaledBmp(result, targetWidth, targetHeight, baos, quality);
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图片缩小一半
     *
     * @param srcBmp
     * @param targetWidth
     * @param targetHeight
     * @param baos
     * @param quality
     * @return
     */
    private static Bitmap generateScaledBmp(Bitmap srcBmp, int targetWidth, int targetHeight, ByteArrayOutputStream baos, int quality) {
        Bitmap result = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, result.getWidth(), result.getHeight());
        canvas.drawBitmap(srcBmp, null, rect, null);
        if (!srcBmp.isRecycled()) {
            srcBmp.recycle();
        }
        result.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return result;
    }
}
