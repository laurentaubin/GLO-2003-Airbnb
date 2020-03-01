package exceptions.bed.BloodType;

import exceptions.BedException;

public class InvalidBloodTypeException extends BedException {
  public InvalidBloodTypeException() {
    super(
        "INVALID_BLOOD_TYPES",
        "blood types should be one or many of " + "O-, O+, AB-, AB+, B-, B+, A- or A+");
  }

  public InvalidBloodTypeException(String s) {
    super(s);
  }
}
