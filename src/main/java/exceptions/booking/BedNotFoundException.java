package exceptions.booking;

public class BedNotFoundException extends Exception {
  public BedNotFoundException() {
    super();
  }

  public BedNotFoundException(String s) {
    super(s);
  }
}
