package exceptions;

public class InvalidPackageNameException extends BedEnumException {
  public InvalidPackageNameException() {
    super();
  }

  public InvalidPackageNameException(String s) {
    super(s);
  }
}
