package exceptions.booking.BedPackage;

public class UnavailableBedPackageException extends Exception {
  public UnavailableBedPackageException() {
    super();
  }

  public UnavailableBedPackageException(String s) {
    super(s);
  }
}
