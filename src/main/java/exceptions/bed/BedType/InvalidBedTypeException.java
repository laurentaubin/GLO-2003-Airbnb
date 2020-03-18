package exceptions.bed.BedType;

import exceptions.BedException;

public class InvalidBedTypeException extends BedException {
  public InvalidBedTypeException() {
    super("INVALID_BED_TYPE", "bed type should be one of latex, memoryFoam or springs");
  }
}
