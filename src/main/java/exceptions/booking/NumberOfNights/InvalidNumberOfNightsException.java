package exceptions.booking.NumberOfNights;

import exceptions.BedException;

public class InvalidNumberOfNightsException extends BedException {
  public InvalidNumberOfNightsException() {
    super("INVALID_NUMBER_OF_NIGHTS", "number of nights should be a number between 1 and 90");
  }
}
