package exceptions.booking.ArrivalDate;

public class UnavailableArrivalDateException extends Exception {
  public UnavailableArrivalDateException() {
    super();
  }

  public UnavailableArrivalDateException(String s) {
    super(s);
  }
}
