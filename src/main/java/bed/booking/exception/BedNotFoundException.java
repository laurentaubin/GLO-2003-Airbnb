package bed.booking.exception;

import bed.AirbnbException;

public class BedNotFoundException extends AirbnbException {
  public BedNotFoundException(String bedNumber) {
    super("BED_NOT_FOUND", "bed with number " + bedNumber + " could not be found");
  }
}
