package bed;

public class ErrorPostResponse {
  private PostStatusResponse error;
  private String description;

  ErrorPostResponse() {}

  ErrorPostResponse(PostStatusResponse error, String description) {
    this.error = error;
    this.description = description;
  }

  public PostStatusResponse getError() {
    return this.error;
  }

  public void setError(PostStatusResponse error) {
    this.error = error;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
