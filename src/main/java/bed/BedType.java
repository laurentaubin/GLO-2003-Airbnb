package bed;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.bed.BedType.InvalidBedTypeException;

public enum BedType {
  @JsonProperty("latex")
  LATEX("latex", 250),
  @JsonProperty("memoryFoam")
  MEMORY_FOAM("memory-foam", 500),
  @JsonProperty("springs")
  SPRINGS("springs", 750);

  private String label;
  private int score;

  BedType(String type, int score) {
    this.label = type;
    this.score = score;
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

  public int getScore() {
    return this.score;
  }

  public int getScore() {
    return this.score;
  }

  @Override
  public String toString() {
    return this.label;
  }
}
