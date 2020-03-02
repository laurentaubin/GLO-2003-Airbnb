package exceptions.booking.Booking;

import exceptions.BookingException;

public class UnallowedBookingException extends BookingException {
  public UnallowedBookingException() {
    super("BOOKING_NOT_ALLOWED", "bed owner cannot book its own bed");
  }

  public UnallowedBookingException(String s) {
    super(s);
  }
}
