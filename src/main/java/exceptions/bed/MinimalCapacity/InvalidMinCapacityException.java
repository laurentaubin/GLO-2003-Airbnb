package exceptions.bed.MinimalCapacity;

public class InvalidMinCapacityException extends exceptions.BedException {
  public InvalidMinCapacityException() {
    super("INVALID_MINIMAL_CAPACITY", "minimal capacity should be a positive number");
  }

  public InvalidMinCapacityException(String s) {
    super(s);
  }
}
