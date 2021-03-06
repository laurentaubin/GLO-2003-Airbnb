package domain.bed.exception;

public class InvalidCleaningFrequencyException extends AirbnbException {
  public InvalidCleaningFrequencyException() {
    super(
        "INVALID_CLEANING_FREQUENCY",
        "cleaning frequency should be one of weekly, monthly," + " annual or never");
  }
}
