package exceptions.booking.BedPackage;

import exceptions.BedException;

public class InvalidBookingPackageException extends BedException {
  public InvalidBookingPackageException() {
    super(
        "PACKAGE_NOT_AVAILABE", "package should be one of bloodthirty, allYouCanDrink, sweetTooth");
  }
}
