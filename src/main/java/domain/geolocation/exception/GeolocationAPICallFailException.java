package domain.geolocation.exception;

import domain.bed.exception.AirbnbException;

public class GeolocationAPICallFailException extends AirbnbException {
  public GeolocationAPICallFailException() {
    super("API_CALL_FAIL", "the call between our app and zippopotamus failed");
  }
}
