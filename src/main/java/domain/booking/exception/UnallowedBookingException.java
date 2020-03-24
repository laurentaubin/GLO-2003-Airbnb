package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class UnallowedBookingException extends AirbnbException {
  public UnallowedBookingException() {
    super("BOOKING_NOT_ALLOWED", "bed owner cannot book its own bed");
  }
}
