package domain.geolocation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Point2D;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class DistanceCalculatorTest {
  String zipCode1 = "12345";
  String zipCode2 = "80202";
  Point2D coord1 = Geolocation.getZipCodeCoordinates(zipCode1);
  Point2D coord2 = Geolocation.getZipCodeCoordinates(zipCode2);

  public DistanceCalculatorTest() throws IOException {}

  @Test
  void
      getDistanceBetweenTwoCoordinates_withValidCoordinates_shouldReturnDistanceBetweenTwoCoordinates()
          throws IOException {
    assertEquals(DistanceCalculator.getDistanceBetweenTwoCoordinates(coord1, coord2), 2960.34, 1);
  }
}
