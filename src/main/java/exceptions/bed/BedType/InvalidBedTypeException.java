package exceptions.bed.BedType;

public class InvalidBedTypeException extends RuntimeException {
  public InvalidBedTypeException() {
    super();
  }

  public InvalidBedTypeException(String s) {
    super(s);
  }
}
