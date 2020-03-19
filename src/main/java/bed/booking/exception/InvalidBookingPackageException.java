package bed.booking.exception;

import bed.AirbnbException;

public class InvalidBookingPackageException extends AirbnbException {
  public InvalidBookingPackageException() {
    super(
        "PACKAGE_NOT_AVAILABLE",
        "package should be one of bloodthirsty, allYouCanDrink, sweetTooth");
  }
}
