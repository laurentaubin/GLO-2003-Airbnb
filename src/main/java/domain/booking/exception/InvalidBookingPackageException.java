package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class InvalidBookingPackageException extends AirbnbException {
  public InvalidBookingPackageException() {
    super(
        "PACKAGE_NOT_AVAILABLE",
        "package should be one of bloodthirsty, allYouCanDrink, sweetTooth");
  }
}
