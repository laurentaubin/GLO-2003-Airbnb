package domain.bed.exception;

public class InvalidNumberOfNightsWithoutMinCapacityException extends AirbnbException {
  public InvalidNumberOfNightsWithoutMinCapacityException() {
    super(
        "NUMBER_OF_NIGHTS_WITHOUT_MINIMAL_CAPACITY",
        "a minimal capacity should be provided along with the number of nights");
  }
}
