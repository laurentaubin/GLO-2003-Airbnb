package exceptions.booking;

import exceptions.BedException;

public class BedAlreadyBookedException extends BedException {
  public BedAlreadyBookedException() {
    super("BED_ALREADY_BOOKED", "bed is already booked for selected dates");
  }
}
