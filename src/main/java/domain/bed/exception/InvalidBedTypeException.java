package domain.bed.exception;

public class InvalidBedTypeException extends AirbnbException {
  public InvalidBedTypeException() {
    super("INVALID_BED_TYPE", "bed type should be one of latex, memoryFoam or springs");
  }

  public InvalidBedTypeException(String s) {
    super(s);
  }
}
