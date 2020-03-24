package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class BookingAlreadyCanceledException extends AirbnbException {
  public BookingAlreadyCanceledException() {
    super("BOOKING_ALREADY_CANCELED", "booking has already been canceled");
  }
}
