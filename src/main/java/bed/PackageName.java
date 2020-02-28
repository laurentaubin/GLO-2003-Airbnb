package bed;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.bed.PackageName.InvalidPackageNameException;

public enum PackageName {
  @JsonProperty("bloodthirsty")
  BLOOD_THIRSTY("bloodthirsty"),
  @JsonProperty("allYouCanDrink")
  ALL_YOU_CAN_DRINK("allYouCanDrink"),
  @JsonProperty("sweetTooth")
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
