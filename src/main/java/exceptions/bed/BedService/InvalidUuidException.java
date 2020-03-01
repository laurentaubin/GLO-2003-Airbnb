package exceptions.bed.BedService;

public class InvalidUuidException extends RuntimeException {
  public InvalidUuidException() {
    super();
  }

  public InvalidUuidException(String s) {
    super(s);
  }
}
