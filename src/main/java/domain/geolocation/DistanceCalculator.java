package domain.geolocation;

import java.awt.geom.Point2D;

public class DistanceCalculator {

  public static double getDistanceBetweenTwoCoordinates(Point2D coord1, Point2D coord2) {
    double lat1 = coord1.getX();
    double lon1 = coord1.getY();

    double lat2 = coord2.getX();
    double lon2 = coord2.getY();

    double distance = Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    double distanceRoundUp = (double) Math.round(distance * 100) / 100;
    return distanceRoundUp;
  }
}
