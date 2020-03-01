package exceptions.bed.CleaningFrequency;

public class InvalidCleaningFrequencyException extends RuntimeException {
  public InvalidCleaningFrequencyException() {
    super();
  }

  public InvalidCleaningFrequencyException(String s) {
    super(s);
  }
}
