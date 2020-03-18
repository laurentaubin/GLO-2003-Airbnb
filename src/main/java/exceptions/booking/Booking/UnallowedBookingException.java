package exceptions.booking.Booking;

import exceptions.BedException;

public class UnallowedBookingException extends BedException {
  public UnallowedBookingException() {
    super("BOOKING_NOT_ALLOWED", "bed owner cannot book its own bed");
  }
}
