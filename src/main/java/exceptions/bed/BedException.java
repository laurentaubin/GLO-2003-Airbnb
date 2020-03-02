package exceptions.bed;

public class BedException extends RuntimeException {
  private String name;
  private String description;

  public BedException() {
    super();
  }

  public BedException(String s) {
    super(s);
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }
}
