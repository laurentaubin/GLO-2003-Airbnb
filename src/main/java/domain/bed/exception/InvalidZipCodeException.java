package domain.bed.exception;

public class InvalidZipCodeException extends AirbnbException {
  public InvalidZipCodeException() {
    super("INVALID_ZIP_CODE", "zip code should be a 5 digits number");
  }
}
