package domain.geolocation.exception;

import domain.bed.exception.AirbnbException;

public class NonExistingZipCodeException extends AirbnbException {
  public NonExistingZipCodeException() {
    super("NON_EXISTING_ZIP_CODE", "zip code is not an existing US postal code");
  }
}
