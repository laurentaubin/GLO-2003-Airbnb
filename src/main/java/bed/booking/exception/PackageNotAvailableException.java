package bed.booking.exception;

import bed.AirbnbException;

public class PackageNotAvailableException extends AirbnbException {
  public PackageNotAvailableException() {
    super("PACKAGE_NOT_AVAILABLE", "selected package is not available for this bed");
  }
}
