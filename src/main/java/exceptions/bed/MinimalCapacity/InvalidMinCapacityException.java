package exceptions.bed.MinimalCapacity;

import exceptions.BedException;

public class InvalidMinCapacityException extends BedException {
  public InvalidMinCapacityException() {
    super("INVALID_MINIMAL_CAPACITY", "minimal capacity should be a positive number");
  }
}
