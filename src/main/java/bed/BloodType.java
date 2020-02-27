package bed;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.bed.BloodType.InvalidBloodTypeException;

public enum BloodType {
  @JsonProperty("O-")
  O_NEG("O-", 1.5),
  @JsonProperty("O+")
  O_POS("O+", 1),
  @JsonProperty("A-")
  A_NEG("A-", 0.6),
  @JsonProperty("A+")
  A_POS("A+", 0.5),
  @JsonProperty("B-")
  B_NEG("B-", 0.5),
  @JsonProperty("B+")
  B_POS("B+", 0.4),
  @JsonProperty("AB-")
  AB_NEG("AB-", 0.2),
  @JsonProperty("AB+")
  AB_POS("AB+", 0.1);

  private String label;
  private double score;

  BloodType(String type, double score) {
    this.label = type;
    this.score = score;
  }

  public static BloodType valueOfLabel(String type) {
    for (BloodType bloodType : values()) {
      if (bloodType.label.equals(type)) {
        return bloodType;
      }
    }
    throw new InvalidBloodTypeException();
  }

  public double getScore() {
    return this.score;
  }

  public double getScore() {
    return this.score;
  }

  @Override
  public String toString() {
    return this.label;
  }
}
