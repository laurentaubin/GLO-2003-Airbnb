package domain.geolocation;

import static org.junit.jupiter.api.Assertions.*;

import domain.geolocation.exception.InvalidZipCodeException;
import domain.geolocation.exception.NonExistingZipCodeException;
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
  void getLocation_withValidZipCode_shouldReturnLatAndLng() throws IOException {
    assertEquals(Geolocation.getLocation("12345").get("latitude").asText(), "42.8333");
    assertEquals(Geolocation.getLocation("12345").get("longitude").asText(), "-74.058");
  }

  @Test
  void getLocation_withNonUSZipCode_shouldThrowNonExistingZipCodeException() {
    assertThrows(NonExistingZipCodeException.class, () -> Geolocation.getLocation("99999"));
  }

  @Test
  void getLocation_withInvalidZipCodeLength_shouldThrowInvalidZipCodeException() {
    assertThrows(InvalidZipCodeException.class, () -> Geolocation.getLocation("1234"));
  }
}
