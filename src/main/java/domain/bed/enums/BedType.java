package domain.bed.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.bed.exception.InvalidBedTypeException;

public enum BedType {
  @JsonProperty("latex")
  LATEX("latex", 400),
  @JsonProperty("memoryFoam")
  MEMORY_FOAM("memoryFoam", 700),
  @JsonProperty("springs")
  SPRINGS("springs", 1000);

  private String label;
  private int maxCapacity;

  BedType(String type, int maxCapacity) {
    this.label = type;
    this.maxCapacity = maxCapacity;
  }

  public static BedType valueOfLabel(String type) {
    for (BedType bedType : values()) {
      if (bedType.label.equals(type)) {
        return bedType;
      }
    }
    throw new InvalidBedTypeException();
  }

  public int getMaxCapacity() {
    return this.maxCapacity;
  }

  @Override
  public String toString() {
    return this.label;
  }
}
