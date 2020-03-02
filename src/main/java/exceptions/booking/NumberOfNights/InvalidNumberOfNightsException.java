package exceptions.booking.NumberOfNights;

import exceptions.BookingException;

public class InvalidNumberOfNightsException extends BookingException {
  public InvalidNumberOfNightsException() {
    super("INVALID_NUMBER_OF_NIGHTS", "number of nights should be a number between 1 and 90");
  }
}
