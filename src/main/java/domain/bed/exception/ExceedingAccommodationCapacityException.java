package domain.bed.exception;

public class ExceedingAccommodationCapacityException extends AirbnbException {
  public ExceedingAccommodationCapacityException() {
    super(
        "EXCEEDING_ACCOMMODATION_CAPACITY",
        "accommodation capacity exceeding maximum viable capacity for the provided bed type");
  }
}
