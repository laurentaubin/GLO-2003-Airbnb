package exceptions;

public abstract class TransactionException extends RuntimeException {
  private String error;
  private String description;

  public TransactionException() {
    super();
  };

  public TransactionException(String s) {
    super(s);
  }

  public TransactionException(String error, String description) {
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
