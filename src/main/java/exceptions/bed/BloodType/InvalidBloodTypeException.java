package exceptions.bed.BloodType;

public class InvalidBloodTypeException extends RuntimeException {
  public InvalidBloodTypeException() {
    super();
  }

  public InvalidBloodTypeException(String s) {
    super(s);
  }
}
