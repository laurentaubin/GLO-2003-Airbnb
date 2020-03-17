package exceptions.booking.BookingService;

import exceptions.BedException;

public class InvalidTenantPublicKeyException extends BedException {
  public InvalidTenantPublicKeyException() {
    super(
        "INVALID_PUBLIC_KEY",
        "BiteCoins account public key should contain only "
            + "alphanumeric characters and have a 256-bits length");
  }
}
