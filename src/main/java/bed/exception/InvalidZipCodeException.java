package bed.exception;

import bed.AirbnbException;

public class InvalidZipCodeException extends AirbnbException {
  public InvalidZipCodeException() {
    super("INVALID_ZIP_CODE", "zip code should be a 5 digits number");
  }
}
