package bed.exception;

import bed.AirbnbException;

public class InvalidCapacityException extends AirbnbException {
  public InvalidCapacityException() {
    super(
        "INVALID_CAPACITY",
        "accommodation capacity exceeding maximum viable capacity for the" + " provided bed type");
  }
}