package domain.bed.exception;

public class InvalidDateWithoutMinCapacityException extends AirbnbException {
  public InvalidDateWithoutMinCapacityException() {
    super(
        "ARRIVAL_DATE_WITHOUT_MINIMAL_CAPACITY",
        "a minimal capacity should be provided along with the arrival date");
  }
}
