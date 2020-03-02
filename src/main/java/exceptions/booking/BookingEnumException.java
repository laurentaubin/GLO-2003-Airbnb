package exceptions.booking;

public class BookingEnumException extends RuntimeException {
  public BookingEnumException() {
    super();
  }

  public BookingEnumException(String s) {
    super(s);
  }
}
