package domain.bed.exception;

public class InvalidCapacityException extends AirbnbException {
  public InvalidCapacityException() {
    super("INVALID_CAPACITY", "capacity should be a positive number");
  }
}
