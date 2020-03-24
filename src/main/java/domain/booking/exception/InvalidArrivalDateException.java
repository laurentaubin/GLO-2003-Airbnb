package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class InvalidArrivalDateException extends AirbnbException {
  public InvalidArrivalDateException() {
    super("INVALID-ARRIVAL-DATE", "Invalid date format: must be yyyy-mm-dd");
  }
}
