package exceptions.booking.Booking;

public class UnavailableBookingException extends Exception {
  public UnavailableBookingException() {
    super();
  }

  public UnavailableBookingException(String s) {
    super(s);
  }
}
