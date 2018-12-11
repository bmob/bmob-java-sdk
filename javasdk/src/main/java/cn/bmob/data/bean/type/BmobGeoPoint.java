package cn.bmob.data.bean.type;

import java.io.Serializable;

public class BmobGeoPoint implements Serializable {

    public static double EARTH_MEAN_RADIUS_KM = 6371.0D;
    public static double EARTH_MEAN_RADIUS_MILE = 3958.8000000000002D;

    private Double latitude = 0.0D;

    private Double longitude = 0.0D;

    private String __type = "GeoPoint";

    private String get__type() {
        return __type;
    }

    /**
     * BmobGeoPoint 默认构造方法
     */
    public BmobGeoPoint() {
    }

    /**
     * BmobGeoPoint 构造方法
     * @param longitude 经度坐标
     * @param latitude 纬度坐标
     */
    public BmobGeoPoint(double longitude, double latitude) {
        setLongitude(longitude);
        setLatitude(latitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * 设置纬度坐标
     * @param latitude 纬度坐标值（范围：90.0D -- -90.0D）
     */
    public void setLatitude(double latitude) {
        if ((latitude > 90.0D) || (latitude < -90.0D)) {
            throw new IllegalArgumentException(
                    "Latitude must be within the range (-90.0, 90.0).");
        }
        this.latitude = latitude;
    }

    /**
     * 获取纬度坐标值
     * @return 返回当前BmobGeoPoint对象的纬度值
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * 设置经度坐标
     * @param longitude 经度坐标值（范围：180.0D -- -180.0D）
     */
    public void setLongitude(double longitude) {
        if ((longitude > 180.0D) || (longitude < -180.0D)) {
            throw new IllegalArgumentException(
                    "Longitude must be within the range (-180.0, 180.0).");
        }
        this.longitude = longitude;
    }

    /**
     * 获取经度坐标值
     * @return 返回当前BmobGeoPoint对象的经度值
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * 获取point这个坐标点与BmobGeoPoint对象的弧度距离
     * @param point 坐标点
     * @return 返回两点之间的弧度距离
     */
    public double distanceInRadiansTo(BmobGeoPoint point) {
        double d2r = 0.0174532925199433D;
        double lat1rad = this.latitude * d2r;
        double long1rad = this.longitude * d2r;
        double lat2rad = point.getLatitude() * d2r;
        double long2rad = point.getLongitude() * d2r;
        double deltaLat = lat1rad - lat2rad;
        double deltaLong = long1rad - long2rad;
        double sinDeltaLatDiv2 = Math.sin(deltaLat / 2.0D);
        double sinDeltaLongDiv2 = Math.sin(deltaLong / 2.0D);
        double a = sinDeltaLatDiv2 * sinDeltaLatDiv2 + Math.cos(lat1rad)
                * Math.cos(lat2rad) * sinDeltaLongDiv2 * sinDeltaLongDiv2;
        a = Math.min(1.0D, a);
        return 2.0D * Math.asin(Math.sqrt(a));
    }

    /**
     * 获取point这个坐标点与BmobGeoPoint对象之间的距离（单位：千米，公里）
     * @param point 坐标点
     * @return 返回两点之间的距离（单位：千米，公里）
     */
    public double distanceInKilometersTo(BmobGeoPoint point) {
        return distanceInRadiansTo(point) * EARTH_MEAN_RADIUS_KM;
    }

    /**
     * 获取poing这个坐标点与BmobGeoPoing对象之间的距离（单位：英里）
     * @param point 坐标点
     * @return 返回两点之间的距离（单位：万里）
     */
    public double distanceInMilesTo(BmobGeoPoint point) {
        return distanceInRadiansTo(point) * EARTH_MEAN_RADIUS_MILE;
    }






}
