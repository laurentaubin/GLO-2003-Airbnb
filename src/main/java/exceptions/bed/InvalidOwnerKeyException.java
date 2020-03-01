package exceptions.bed;

import exceptions.BedException;

public class InvalidOwnerKeyException extends BedException {
  public InvalidOwnerKeyException() {
    super(
        "INVALID_PUBLIC_KEY",
        "BiteCoins account public key should contain only "
            + "alphanumeric characters and have a 256-bits length");
  }
}
