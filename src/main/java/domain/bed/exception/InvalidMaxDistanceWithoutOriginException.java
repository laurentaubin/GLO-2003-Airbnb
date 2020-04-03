package domain.bed.exception;

public class InvalidMaxDistanceWithoutOriginException extends AirbnbException {
  public InvalidMaxDistanceWithoutOriginException() {
    super(
        "MAX_DISTANCE_WITHOUT_ORIGIN",
        "an origin point should be provided along with the maximum distance");
  }
}
