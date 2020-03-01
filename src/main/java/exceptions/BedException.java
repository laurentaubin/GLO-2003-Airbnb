package exceptions;

public abstract class BedException extends RuntimeException {
  private String error;
  private String description;

  public BedException() {
    super();
  };

  public BedException(String s) {
    super(s);
  }

  public BedException(String error, String description) {
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
