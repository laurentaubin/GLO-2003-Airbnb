package bed.exception;

import bed.AirbnbException;

public class InvalidLodgingModeException extends AirbnbException {
  public InvalidLodgingModeException() {
    super(
        "INVALID_LODGING_MODE", "provided lodging mode is not one of: 'private', 'cohabitation'.");
  }
}
