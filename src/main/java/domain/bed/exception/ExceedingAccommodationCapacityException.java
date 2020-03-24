package domain.bed.exception;

public class ExceedingAccommodationCapacityException extends AirbnbException {
  public ExceedingAccommodationCapacityException() {
    super(
        "EXCEEDING_ACCOMODATION_CAPACITY",
        "accomodation capacity exceeding maximum viable" + "capacity for the provided bed type");
  }
}
