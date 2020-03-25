package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class InvalidArrivalDateException extends AirbnbException {
  public InvalidArrivalDateException() {
    super("INVALID_ARRIVAL_DATE", "arrival date should be formatted as YYYY-MM-DD");
  }
}
