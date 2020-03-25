package domain.bed.exception;

public class ArrivalDateInPastException extends AirbnbException {
  public ArrivalDateInPastException() {
    super("ARRIVAL_DATE_IN_THE_PAST", "cannot book a stay in the past");
  }
}
