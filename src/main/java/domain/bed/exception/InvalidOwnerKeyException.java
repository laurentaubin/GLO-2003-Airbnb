package domain.bed.exception;

public class InvalidOwnerKeyException extends AirbnbException {
  public InvalidOwnerKeyException() {
    super(
        "INVALID_PUBLIC_KEY",
        "BiteCoins account public key should contain only "
            + "alphanumeric characters and have a 256-bits length");
  }
}
