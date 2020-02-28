package exceptions.bed.BloodType;

import exceptions.bed.BedEnumException;

public class InvalidBloodTypeException extends BedEnumException {
  public InvalidBloodTypeException() {
    super();
  }

  public InvalidBloodTypeException(String s) {
    super(s);
  }
}
