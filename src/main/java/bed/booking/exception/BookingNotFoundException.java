package bed.booking.exception;

import bed.AirbnbException;

public class BookingNotFoundException extends AirbnbException {
  public BookingNotFoundException(String bookingNumber) {
    super("BOOKING_NOT_FOUND", "booking with number " + bookingNumber + " could not be found");
  }
}
