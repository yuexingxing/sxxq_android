package com.sanshao.bs.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogAdapter;
import com.sanshao.commonui.dialog.CommonDialogInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：打开手机已安装地图相关工具类
 * </br>
 */
public class OpenLocalMapUtil {

    /**
     * 当前位置
     */
    public static double[] START_LATLON = {31.294335, 121.493748};
    /**
     * 目的地
     */
    private static double[] DESTINATION_TA_LATLON = {31.294335, 121.493748};
    private static boolean isOpened;
    public static String SNAME = "上海市长宁区中山公园";
    public static String DNAME = "上海市长宁区虹桥国际机场";
    public static String CITY = "上海市";
    public static String APP_NAME = "三少爱美";
    private static String SRC = "thirdapp.navi.beiing.openlocalmapdemo";

    /**
     * 地图应用是否安装
     *
     * @return
     */
    public static boolean isGdMapInstalled() {
        return isInstallPackage("com.autonavi.minimap");
    }

    public static boolean isBaiduMapInstalled() {
        return isInstallPackage("com.baidu.BaiduMap");
    }

    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 获取打开百度地图应用uri [http://lbsyun.baidu.com/index.php?title=uri/api/android]
     *
     * @param originLat
     * @param originLon
     * @param desLat
     * @param desLon
     * @return
     */
    public static String getBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String src) {
        String uri = "intent://map/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&src=%8$s#Intent;" +
                "scheme=bdapp;package=com.baidu.BaiduMap;end";

        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, src);
    }

    /**
     * 获取打开高德地图应用uri
     */
    public static String getGdMapUri(String appName, String slat, String slon, String sname, String dlat, String dlon, String dname) {
        String uri = "androidamap://route?sourceApplication=%1$s&slat=%2$s&slon=%3$s&sname=%4$s&dlat=%5$s&dlon=%6$s&dname=%7$s&dev=0&m=0&t=2";
        return String.format(uri, appName, slat, slon, sname, dlat, dlon, dname);
    }


    /**
     * 网页版百度地图 有经纬度
     *
     * @param originLat
     * @param originLon
     * @param originName  ->注：必填
     * @param desLat
     * @param desLon
     * @param destination
     * @param region      : 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。-->注：必填，不填不会显示导航路线
     * @param appName
     * @return
     */
    public static String getWebBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String appName) {
        String uri = "http://api.map.baidu.com/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&output=html" +
                "&src=%8$s";
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, appName);
    }


    /**
     * 百度地图定位经纬度转高德经纬度
     *
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * 高德地图定位经纬度转百度经纬度
     *
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    /**
     * @param slat
     * @param slon
     * @param address 当前位置
     * @param city    所在城市
     */
    public static void openLocalMap(Context context, double slat, double slon, String address, String city) {
        if (OpenLocalMapUtil.isBaiduMapInstalled() && OpenLocalMapUtil.isGdMapInstalled()) {
            chooseOpenMap(context, slat, slon, address, city);
        } else {
            openBaiduMap(context, slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME, city);

            if (!isOpened) {
                openGaoDeMap(context, slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME);
            }

            if (!isOpened) {
                //打开网页地图
                openWebMap(context, slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME, city);
            }
        }

    }

    /**
     * 如果两个地图都安装，提示选择
     *
     * @param slat
     * @param slon
     * @param address
     * @param city
     */
    private static void chooseOpenMap(Context context, final double slat, final double slon, final String address, final String city) {

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo("百度地图"));
        commonDialogInfoList.add(new CommonDialogInfo("高德地图"));

        new CommonBottomDialog()
                .init(context)
                .setData(commonDialogInfoList)
                .setOnItemClickListener(new CommonDialogAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(CommonDialogInfo commonDialogInfo) {
                        if (commonDialogInfo.position == 0) {
                            openBaiduMap(context, slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME, city);
                        } else {
                            openGaoDeMap(context, slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME);
                        }
                    }
                }).show();
    }

    /**
     * 打开百度地图
     */
    private static void openBaiduMap(Context context, double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        if (OpenLocalMapUtil.isBaiduMapInstalled()) {
            try {
                String uri = OpenLocalMapUtil.getBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname,
                        String.valueOf(dlat), String.valueOf(dlon), dname, city, SRC);
                Intent intent = Intent.parseUri(uri, 0);
                context.startActivity(intent); //启动调用

                isOpened = true;
            } catch (Exception e) {
                isOpened = false;
                e.printStackTrace();
            }
        } else {
            isOpened = false;
        }
    }

    /**
     * 打开高德地图
     */
    private static void openGaoDeMap(Context context, double slat, double slon, String sname, double dlat, double dlon, String dname) {
        if (OpenLocalMapUtil.isGdMapInstalled()) {
            try {
                CoordinateConverter converter = new CoordinateConverter(context);
                converter.from(CoordinateConverter.CoordType.BAIDU);
                DPoint sPoint = null, dPoint = null;
                try {
                    converter.coord(new DPoint(slat, slon));
                    sPoint = converter.convert();
                    converter.coord(new DPoint(dlat, dlon));
                    dPoint = converter.convert();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sPoint != null && dPoint != null) {
                    String uri = OpenLocalMapUtil.getGdMapUri(APP_NAME, String.valueOf(sPoint.getLatitude()), String.valueOf(sPoint.getLongitude()),
                            sname, String.valueOf(dPoint.getLatitude()), String.valueOf(dPoint.getLongitude()), dname);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.autonavi.minimap");
                    intent.setData(Uri.parse(uri));
                    context.startActivity(intent); //启动调用

                    isOpened = true;
                }
            } catch (Exception e) {
                isOpened = false;
                e.printStackTrace();
            }
        } else {
            isOpened = false;
        }
    }

    /**
     * 打开浏览器进行百度地图导航
     */
    public static void openWebMap(Context context, double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        Uri mapUri = Uri.parse(OpenLocalMapUtil.getWebBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname,
                String.valueOf(dlat), String.valueOf(dlon),
                dname, city, APP_NAME));
        Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
        context.startActivity(loction);
    }
}