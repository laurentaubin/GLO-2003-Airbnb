package exceptions.bed;

import exceptions.BedException;

public class InvalidZipCodeException extends BedException {
  public InvalidZipCodeException() {
    super("INVALID_ZIP_CODE", "zip code should be a 5 digits number");
  }
}
