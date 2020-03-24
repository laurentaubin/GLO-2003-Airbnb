package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class CancelationNotAllowedException extends AirbnbException {
  public CancelationNotAllowedException() {
    super("CANCELATION_NOT_ALLOWED", "cancelation period for this booking is over");
  }
}
