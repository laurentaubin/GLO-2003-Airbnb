package exceptions;

public abstract class BedEnumException extends RuntimeException {
  public BedEnumException() {
    super();
  }

  public BedEnumException(String s) {
    super(s);
  }
}
