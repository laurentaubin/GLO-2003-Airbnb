package exceptions.booking;

import exceptions.BedException;

public class BedNotFoundException extends BedException {
  public BedNotFoundException(String bedNumber) {
    super("BED_NOT_FOUND", "bed with number " + bedNumber + " could not be found");
  }
}
