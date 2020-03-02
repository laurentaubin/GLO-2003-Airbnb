package exceptions.booking.BedPackage;

public class InvalidBedPackageException extends Exception {
  public InvalidBedPackageException() {
    super();
  }

  public InvalidBedPackageException(String s) {
    super(s);
  }
}
