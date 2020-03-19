package bed.exception;

import bed.AirbnbException;

public class InvalidPackageNameException extends AirbnbException {
  public InvalidPackageNameException() {
    super(
        "INVALID_PACKAGES",
        "packages should be an array of package name with " + "its corresponding price per night");
  }
}
