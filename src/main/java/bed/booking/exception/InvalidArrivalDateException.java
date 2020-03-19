package bed.booking.exception;

import bed.AirbnbException;

public class InvalidArrivalDateException extends AirbnbException {
  public InvalidArrivalDateException() {
    super("INVALID-ARRIVAL-DATE", "Invalid date format: must be yyyy-mm-dd");
  }
}
