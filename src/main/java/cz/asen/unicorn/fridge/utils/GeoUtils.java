package cz.asen.unicorn.fridge.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GeoUtils {

    // Radius of the earth in kilometers
    private static final double EARTH_RADIUS = 6371;

    /**
     * Calculates the haversine distance between two sets of coordinates
     * @param lat1 the latitude of the first coordinate
     * @param lon1 the longitude of the first coordinate
     * @param lat2 the latitude of the second coordinate
     * @param lon2 the longitude of the second coordinate
     * @return the distance in kilometers
     */
    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        // convert degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // calculate haversine distance
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // convert back to kilometers
        return EARTH_RADIUS * c;
    }

    /**
     * Checks if the location is within a certain radius
     * @param centerLat the latitude of the center
     * @param centerLon the longitude of the center
     * @param checkLat the latitude of the location to check
     * @param checkLon the longitude of the location to check
     * @param radius the radius in kilometers
     * @return true if the location is within the radius; false otherwise
     */
    public static boolean withinRadius(double centerLat, double centerLon, double checkLat, double checkLon, double radius) {
        return haversineDistance(centerLat, centerLon, checkLat, checkLon) <= radius;
    }
}