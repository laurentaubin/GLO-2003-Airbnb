package exceptions.bed.CleaningFrequency;

import exceptions.bed.BedEnumException;

public class InvalidCleaningFrequencyException extends BedEnumException {
  public InvalidCleaningFrequencyException() {
    super();
  }

  public InvalidCleaningFrequencyException(String s) {
    super(s);
  }
}
