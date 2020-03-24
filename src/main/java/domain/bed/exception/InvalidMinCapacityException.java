package domain.bed.exception;

public class InvalidMinCapacityException extends AirbnbException {
  public InvalidMinCapacityException() {
    super("INVALID_MINIMAL_CAPACITY", "minimal capacity should be a positive number");
  }
}
