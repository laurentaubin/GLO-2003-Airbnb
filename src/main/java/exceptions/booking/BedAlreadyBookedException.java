package exceptions.booking;

import exceptions.BookingException;

public class BedAlreadyBookedException extends BookingException {
  public BedAlreadyBookedException() {
    super("BED_ALREADY_BOOKED", "bed is already booked for selected dates");
  }
}
