package domain.geolocation;

import static org.junit.jupiter.api.Assertions.*;

import domain.geolocation.exception.InvalidZipCodeException;
import domain.geolocation.exception.NonExistingZipCodeException;
import java.awt.geom.Point2D;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class GeolocationTest {

  @Test
  void creatingGeolocation_withValidZip_shouldNotThrow() {
    assertDoesNotThrow(() -> new Geolocation());
  }

  @Test
  void creatingGeolocation_withValidParameters_shouldNotThrow() {
    assertDoesNotThrow(() -> new Geolocation());
  }

  @Test
  void validateZipCode_withValidZipCode_shouldReturnTrue() throws IOException {
    assertEquals(Geolocation.validateZipCode("12345"), true);
  }

  @Test
  void validateZipCode_withInvalidZipCodeLength_shouldThrowInvalidZipCodeException() {
    assertThrows(InvalidZipCodeException.class, () -> Geolocation.validateZipCode("1234"));
  }

  @Test
  void validateZipCode_withInvalidZipCodeAlpha_shouldThrowInvalidZipCodeException() {
    assertThrows(InvalidZipCodeException.class, () -> Geolocation.validateZipCode("1245E"));
  }

  @Test
  void validateZipCode_withNonUSZipCode_shouldThrowNonExistingZipCodeException() {
    assertThrows(NonExistingZipCodeException.class, () -> Geolocation.validateZipCode("99999"));
  }

  @Test
  void GETRequest_withValidZipCode_shouldReturn12345() throws IOException {
    assertEquals(Geolocation.GETRequest("12345").get("post code").asText(), "12345");
  }

  @Test
  void getZipCodeCoordinates_withValidZipCode_shouldReturnLatAndLng() throws IOException {
    Point2D coord = Geolocation.getZipCodeCoordinates("12345");
    assertEquals(coord.getX(), 4736.249314, 0.000001);
    assertEquals(coord.getY(), -6045.712671, 0.000001);
  }

  @Test
  void coordinatesDegreeConverter_withValidZipCode_shouldReturnlatLongConvertedToDegrees()
      throws IOException {

    Point2D coordinates = new Point2D.Double(42.8333, -74.058);
    Point2D convertedCoord = Geolocation.coordinatesDegreeConverter(coordinates);

    assertEquals(convertedCoord.getX(), 4736.249314, 0.000001);
    assertEquals(convertedCoord.getY(), -6045.712671, 0.000001);
  }

  @Test
  void getZipCodeCoordinates_withNonUSZipCode_shouldThrowNonExistingZipCodeException() {
    assertThrows(
        NonExistingZipCodeException.class, () -> Geolocation.getZipCodeCoordinates("99999"));
  }

  @Test
  void getZipCodeCoordinates_withInvalidZipCodeLength_shouldThrowInvalidZipCodeException() {
    assertThrows(InvalidZipCodeException.class, () -> Geolocation.getZipCodeCoordinates("1234"));
  }
}
