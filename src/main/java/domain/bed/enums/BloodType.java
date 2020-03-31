package domain.bed.enums;

import domain.bed.exception.InvalidBloodTypeException;

public enum BloodType {
  O_NEG("O-"),
  O_POS("O+"),
  A_NEG("A-"),
  A_POS("A+"),
  B_NEG("B-"),
  B_POS("B+"),
  AB_NEG("AB-"),
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
    throw new InvalidBloodTypeException();
  }

  @Override
  public String toString() {
    return this.label;
  }
}
