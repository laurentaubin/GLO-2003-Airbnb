package bed;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.bed.BedType.InvalidBedTypeException;

public enum BedType {
  @JsonProperty("latex")
  LATEX("latex"),
  @JsonProperty("memoryFoam")
  MEMORY_FOAM("memoryFoam"),
  @JsonProperty("springs")
  SPRINGS("springs");

  private String label;

  BedType(String type) {
    this.label = type;
  }

  public static BedType valueOfLabel(String type) {
    for (BedType bedType : values()) {
      if (bedType.label.equals(type)) {
        return bedType;
      }
    }
    throw new InvalidBedTypeException();
  }

  @Override
  public String toString() {
    return this.label;
  }
}