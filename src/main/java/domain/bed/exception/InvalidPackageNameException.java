package domain.bed.exception;

public class InvalidPackageNameException extends AirbnbException {
  public InvalidPackageNameException() {
    super(
        "INVALID_PACKAGE",
        "packages should be an array of package name with " + "its corresponding price per night");
  }
}
