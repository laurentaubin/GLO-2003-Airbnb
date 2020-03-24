package domain.booking.exception;

import domain.bed.exception.AirbnbException;

public class InvalidTenantPublicKeyException extends AirbnbException {
  public InvalidTenantPublicKeyException() {
    super(
        "INVALID_PUBLIC_KEY",
        "BiteCoins account public key should contain only "
            + "alphanumeric characters and have a 256-bits length");
  }
}
