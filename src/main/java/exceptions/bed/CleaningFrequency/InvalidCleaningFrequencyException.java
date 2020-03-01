package exceptions.bed.CleaningFrequency;

import exceptions.BedException;

public class InvalidCleaningFrequencyException extends BedException {
  public InvalidCleaningFrequencyException() {
    super(
        "INVALID_CLEANING_FREQUENCY",
        "cleaning frequency should be one of weekly, monthly," + " annual or never");
  }

  public InvalidCleaningFrequencyException(String s) {
    super(s);
  }
}
