package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class InvalidNumberOfNightsException extends AirbnbException {
  public InvalidNumberOfNightsException() {
    super("INVALID_NUMBER_OF_NIGHTS", "number of nights should be a number between 1 and 90");
  }
}
