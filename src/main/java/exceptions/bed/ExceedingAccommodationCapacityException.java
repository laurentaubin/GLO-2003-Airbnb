package exceptions.bed;

import exceptions.BedException;

public class ExceedingAccommodationCapacityException extends BedException {
  public ExceedingAccommodationCapacityException() {
    super(
        "EXCEEDING_ACCOMODATION_CAPACITY",
        "accomodation capacity exceeding maximum viable" + "capacity for the provided bed type");
  }
}
