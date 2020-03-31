package domain.bed.enums;

import domain.bed.exception.InvalidPackageNameException;

public enum PackageName {
  BLOOD_THIRSTY("bloodthirsty"),
  ALL_YOU_CAN_DRINK("allYouCanDrink"),
  SWEET_TOOTH("sweetTooth");

  private String label;

  PackageName(String label) {
    this.label = label;
  }

  public static PackageName valueOfLabel(String bedPackage) throws InvalidPackageNameException {
    for (PackageName name : values()) {
      if (name.label.equals(bedPackage)) {
        return name;
      }
    }
    throw new InvalidPackageNameException();
  }

  @Override
  public String toString() {
    return this.label;
  }
}
