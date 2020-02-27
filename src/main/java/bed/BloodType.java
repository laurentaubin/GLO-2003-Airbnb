package bed;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BloodType {
  @JsonProperty("O-")
  O_NEG("O-"),
  @JsonProperty("O+")
  O_POS("O+"),
  @JsonProperty("A-")
  A_NEG("A-"),
  @JsonProperty("A+")
  A_POS("A+"),
  @JsonProperty("B-")
  B_NEG("B-"),
  @JsonProperty("B+")
  B_POS("B+"),
  @JsonProperty("AB-")
  AB_NEG("AB-"),
  @JsonProperty("AB+")
  AB_POS("AB+");

  private String label;

  BloodType(String type) {
    this.label = type;
  }

  public static BloodType valueOfLabel(String type) {
    for (BloodType bloodType : values()) {
      if (bloodType.label.equals(type)) {
        return bloodType;
      }
    }
    throw new IllegalArgumentException("Invalid blood type");
  }

  @Override
  public String toString() {
    return this.label;
  }
}
