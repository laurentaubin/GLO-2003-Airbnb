package exceptions.booking;

import exceptions.BedException;

public class BookingNotFoundException extends BedException {
  public BookingNotFoundException(String bookingNumber) {
    super("BOOKING_NOT_FOUND", "bed.booking with number " + bookingNumber + " could not be found");
  }
}
