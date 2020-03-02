package exceptions;

public class BookingException extends RuntimeException {
  private String error;
  private String description;

  public BookingException() {
    super();
  };

  public BookingException(String s) {
    super(s);
  }

  public BookingException(String error, String description) {
    this.error = error;
    this.description = description;
  }

  public String getError() {
    return this.error;
  }

  public String getDescription() {
    return this.description;
  }
}
