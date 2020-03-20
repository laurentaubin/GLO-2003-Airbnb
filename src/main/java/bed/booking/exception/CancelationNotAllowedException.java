package bed.booking.exception;

import bed.AirbnbException;

public class CancelationNotAllowedException extends AirbnbException {
  public CancelationNotAllowedException() {
    super("CANCELATION_NOT_ALLOWED", "cancelation period for this booking is over");
  }
}
