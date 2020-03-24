package domain.bed.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.bed.exception.InvalidBedTypeException;

public enum BedType {
  @JsonProperty("latex")
  LATEX("latex", 250, 400),
  @JsonProperty("memoryFoam")
  MEMORY_FOAM("memoryFoam", 500, 700),
  @JsonProperty("springs")
  SPRINGS("springs", 750, 1000);

  private String label;
  private int score;
  private int maxCapacity;

  BedType(String type, int score, int maxCapacity) {
    this.label = type;
    this.score = score;
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

  public int getScore() {
    return this.score;
  }

  public int getMaxCapacity() {
    return this.maxCapacity;
  }

  @Override
  public String toString() {
    return this.label;
  }
}
