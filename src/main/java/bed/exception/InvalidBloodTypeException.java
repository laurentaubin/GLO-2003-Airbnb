package bed.exception;

import bed.AirbnbException;

public class InvalidBloodTypeException extends AirbnbException {
  public InvalidBloodTypeException() {
    super(
        "INVALID_BLOOD_TYPES",
        "blood types should be one or many of " + "O-, O+, AB-, AB+, B-, B+, A- or A+");
  }
}
