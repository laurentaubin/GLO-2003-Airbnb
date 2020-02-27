package bed;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BedPackage {
  private Name name;
  private double pricePerNight;

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public double getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(double pricePerNight) {
    this.pricePerNight = pricePerNight;
  }

  public enum Name {
    @JsonProperty("bloodthirsty")
    BLOOD_THIRSTY("bloodthirsty"),
    @JsonProperty("allYouCanDrink")
    ALL_YOU_CAN_DRINK("allYouCanDrink"),
    @JsonProperty("sweetTooth")
    SWEET_TOOTH("sweetTooth");

    private String label;

    Name(String label) {
      this.label = label;
    }

    public static Name valueOfLabel(String bedPackage) {
      for (Name name : values()) {
        if (name.label.equals(bedPackage)) {
          return name;
        }
      }
      throw new IllegalArgumentException("Invalid package name");
    }

    @Override
    public String toString() {
      return this.label;
    }
  }

  public BedPackage(Name name, double pricePerNight) {
    this.setName(name);
    this.setPricePerNight(pricePerNight);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    BedPackage other = (BedPackage) obj;
    return this.getName().equals(other.getName())
        && this.getPricePerNight() == other.getPricePerNight();
  }
}
