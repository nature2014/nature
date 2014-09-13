package util;

import java.text.DecimalFormat;

/**
 * Created by pli on 14-4-19.
 */

public class EarthGpsDistanceUtil {

    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        DecimalFormat df = new DecimalFormat("#.000");
        return Double.valueOf(df.format(s));
    }

    public static void main(String[] args) {
        double distnace = EarthGpsDistanceUtil.getDistance(31.936, 118.844, 32.063, 118.790);
        System.out.println(distnace);
    }
}
