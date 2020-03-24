package domain.bed.exception;

public class InvalidSweetToothPackageException extends AirbnbException {

  public InvalidSweetToothPackageException() {
    super(
        "CANT_OFFER_SWEET_TOOTH_PACKAGE",
        "in order to offer sweetTooth package, you must also offer the bloodthirsty and "
            + "allYouCanDrink packages");
  }
}
