package exceptions.bed;

import exceptions.BedException;

public class InvalidCapacityException extends BedException {
  public InvalidCapacityException() {
    super(
        "INVALID_CAPACITY",
        "accommodation capacity exceeding maximum viable capacity for the" + " provided bed type");
  }
}
