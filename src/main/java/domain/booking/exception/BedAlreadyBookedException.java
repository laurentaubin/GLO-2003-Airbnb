package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class BedAlreadyBookedException extends AirbnbException {
  public BedAlreadyBookedException() {
    super("BED_ALREADY_BOOKED", "bed is already booked for selected dates");
  }
}
