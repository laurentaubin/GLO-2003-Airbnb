package exceptions.booking.ArrivalDate;

public class InvalidArrivalDateException extends Exception {
  public InvalidArrivalDateException() {
    super();
  }

  public InvalidArrivalDateException(String s) {
    super(s);
  }
}
