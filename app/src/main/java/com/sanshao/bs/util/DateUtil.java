package com.sanshao.bs.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    private static final String TAG = DateUtil.class.getSimpleName();

    public static String getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    // 转换日期格式
    public static String timeFormat(String time) {
        String temp = time.replace("Z", " UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date date = null;
        try {
            date = sdf.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String finalDate = newDate.format(date);
        return finalDate;
    }

    /**
     * 日期往后或前加减num天
     *
     * @param day
     * @param num
     * @return
     */
    public static String getDateStr(String day, int num) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果需要向后计算日期 -改为+
        Date newDate2 = new Date(nowDate.getTime() + (long) num * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    /**
     * 获取两个日期间隔天数
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int getDiffDay(String beginTime, String endTime) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeMillis = System.currentTimeMillis();
        if (null == beginTime) {
            beginTime = stampToDate(timeMillis);
        }

        if (null == endTime) {
            endTime = stampToDate(timeMillis);
        }

        //将时间戳转为日期格式
        Log.e("tag timeMillis =", "" + timeMillis);
        Log.e("tag curTime =", "" + beginTime);
        Log.e("tag endTime =", "" + endTime);
        Date curDate = null;
        Date endDate = null;

        try {
            curDate = df.parse(beginTime);
            endDate = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = endDate.getTime() - curDate.getTime();
        Log.i("tag", "时间差=" + diff);

        //以天数为单位取整
        int day = (int) (diff / (1000 * 60 * 60 * 24));
        //以小时为单位取整
        Long hour = (diff / (60 * 60 * 1000) - day * 24);
        //以分钟为单位取整
        Long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        //以秒为单位
        Long second = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        Log.e(TAG, "day =" + day);
        Log.e(TAG, "hour =" + hour);
        Log.e(TAG, "min =" + min);
        Log.e(TAG, "second =" + second);
        return day;
    }

    /**
     * 将时间戳转换为时间
     */

    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
