package exceptions.booking.BookingService;

import exceptions.BookingException;

public class InvalidTenantPublicKeyException extends BookingException {
  public InvalidTenantPublicKeyException() {
    super(
        "INVALID_PUBLIC_KEY",
        "BiteCoins account public key should contain only "
            + "alphanumeric characters and have a 256-bits length");
  }

  public InvalidTenantPublicKeyException(String s) {
    super(s);
  }
}
