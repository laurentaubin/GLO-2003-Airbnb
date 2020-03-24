package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class PackageNotAvailableException extends AirbnbException {
  public PackageNotAvailableException() {
    super("PACKAGE_NOT_AVAILABLE", "selected package is not available for this bed");
  }
}
