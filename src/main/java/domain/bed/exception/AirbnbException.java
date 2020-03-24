package domain.bed.exception;

public abstract class AirbnbException extends RuntimeException {
  private String error;
  private String description;

  public AirbnbException() {
    super();
  };

  public AirbnbException(String s) {
    super(s);
  }

  public AirbnbException(String error, String description) {
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
