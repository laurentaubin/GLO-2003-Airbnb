package exceptions.bed.BedType;

import exceptions.bed.BedEnumException;

public class InvalidBedTypeException extends BedEnumException {
  public InvalidBedTypeException() {
    super();
  }

  public InvalidBedTypeException(String s) {
    super(s);
  }
}
