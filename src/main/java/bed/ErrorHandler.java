package bed;

public class ErrorHandler {
  private String error;
  private String description;

  public ErrorHandler(String error, String description) {
    this.error = error;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getError() {
    return error;
  }
}
