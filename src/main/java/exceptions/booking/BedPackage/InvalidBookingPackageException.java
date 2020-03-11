package exceptions.booking.BedPackage;

import exceptions.BookingException;

public class InvalidBookingPackageException extends BookingException {
  public InvalidBookingPackageException() {
    super(
        "PACKAGE_NOT_AVAILABLE",
        "package should be one of bloodthirsty, allYouCanDrink, sweetTooth");
  }
}
