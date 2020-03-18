package exceptions.booking.ArrivalDate;

import exceptions.BedException;

public class InvalidArrivalDateException extends BedException {
  public InvalidArrivalDateException() {
    super("INVALID-ARRIVAL-DATE", "Invalid date format: must be yyyy-mm-dd");
  }
}
