package exceptions.booking.BedPackage;

import exceptions.BookingException;

public class PackageNotAvailableException extends BookingException {
  public PackageNotAvailableException() {
    super("PACKAGE_NOT_AVAILABLE", "selected package is not available for this bed");
  }
}
