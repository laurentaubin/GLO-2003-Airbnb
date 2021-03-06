package domain.bed.enums;

import domain.bed.exception.InvalidLodgingModeException;

public enum LodgingMode {
  PRIVATE("private"),
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
