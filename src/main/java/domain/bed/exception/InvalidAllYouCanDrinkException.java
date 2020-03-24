package domain.bed.exception;

public class InvalidAllYouCanDrinkException extends AirbnbException {
  public InvalidAllYouCanDrinkException() {
    super(
        "CANT_OFFER_ALL_YOU_CAN_DRINK_PACKAGE",
        "in order to offer allYouCanDrink package,"
            + " you must also offer the bloodthirsty package");
  }
}
