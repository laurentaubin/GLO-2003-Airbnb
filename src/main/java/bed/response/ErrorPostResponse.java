package bed.response;

public class ErrorPostResponse {
  private String error;
  private String description;

  public ErrorPostResponse() {}

  ErrorPostResponse(String error, String description) {
    this.error = error;
    this.description = description;
  }

  public String getError() {
    return this.error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
