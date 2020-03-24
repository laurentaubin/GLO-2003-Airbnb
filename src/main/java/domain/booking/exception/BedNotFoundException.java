package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class BedNotFoundException extends AirbnbException {
  public BedNotFoundException(String bedNumber) {
    super("BED_NOT_FOUND", "bed with number " + bedNumber + " could not be found");
  }
}
