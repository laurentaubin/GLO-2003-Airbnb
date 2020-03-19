package bed.booking.exception;

import bed.AirbnbException;

public class UnallowedBookingException extends AirbnbException {
  public UnallowedBookingException() {
    super("BOOKING_NOT_ALLOWED", "bed owner cannot book its own bed");
  }
}
