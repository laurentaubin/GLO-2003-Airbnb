package exceptions.booking.BookingService;

public class InvalidUuidException extends Exception {
  public InvalidUuidException() {
    super();
  }

  public InvalidUuidException(String s) {
    super(s);
  }
}
