package exceptions.booking.BedPackage;

import exceptions.BedException;

public class InvalidBookingPackageException extends BedException {
  public InvalidBookingPackageException() {
    super(
        "PACKAGE_NOT_AVAILABLE",
        "package should be one of bloodthirsty, allYouCanDrink, sweetTooth");
  }
}
