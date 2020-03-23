package bed;

import bed.exception.InvalidLodgingModeException;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum LodgingMode {
  @JsonProperty("private")
  PRIVATE("private"),
  @JsonProperty("cohabitation")
  COHABITATION("cohabitation");

  private String label;

  LodgingMode(String mode) {
    this.label = mode;
  }

  public static LodgingMode valueOfLabel(String label) {
    for (LodgingMode lodgingMode : values()) {
      if (lodgingMode.label.equals(label)) {
        return lodgingMode;
      }
    }
    throw new InvalidLodgingModeException();
  }

  @Override
  public String toString() {
    return this.label;
  }
}
