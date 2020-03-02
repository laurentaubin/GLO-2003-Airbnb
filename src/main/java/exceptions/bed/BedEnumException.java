package exceptions.bed;

public abstract class BedEnumException extends RuntimeException {
  private String name;
  private String description;

  public BedEnumException() {
    super();
  }

  public BedEnumException(String s) {
    super(s);
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }
}
