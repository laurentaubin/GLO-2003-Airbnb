package exceptions.booking.BedPackage;

import exceptions.BedException;

public class PackageNotAvailableException extends BedException {
  public PackageNotAvailableException() {
    super("PACKAGE_NOT_AVAILABLE", "selected package is not available for this bed");
  }
}
