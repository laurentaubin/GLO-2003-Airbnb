package domain.bed.exception;

public class InvalidMaxDistanceException extends AirbnbException {
  public InvalidMaxDistanceException() {
    super("INVALID_MAX_DISTANCE", "distance should be a number greater than 0");
  }
}
