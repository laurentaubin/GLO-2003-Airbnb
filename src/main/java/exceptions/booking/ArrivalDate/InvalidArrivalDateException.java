package exceptions.booking.ArrivalDate;

import exceptions.BookingException;

public class InvalidArrivalDateException extends BookingException {
  public InvalidArrivalDateException() {
    super("INVALID-ARRIVAL-DATE", "Invalid date format: must be yyyy-mm-dd");
  }

  public InvalidArrivalDateException(String s) {
    super(s);
  }
}
