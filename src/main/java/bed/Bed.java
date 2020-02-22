package bed;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bed {
  private String ownerPublicKey;
  private String zipCode;
  private BedType bedType;
  private CleaningFrequency cleaningFrequency;
  private BloodType[] bloodTypes;
  private int capacity;
  private BedPackage[] packages;

  public String getOwnerPublicKey() {
    return ownerPublicKey;
  }

  public void setOwnerPublicKey(String ownerPublicKey) {
    this.ownerPublicKey = ownerPublicKey;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public BedType getBedType() {
    return bedType;
  }

  public void setBedType(BedType bedType) {
    this.bedType = bedType;
  }

  public CleaningFrequency getCleaningFrequency() {
    return cleaningFrequency;
  }

  public void setCleaningFrequency(CleaningFrequency cleaningFrequency) {
    this.cleaningFrequency = cleaningFrequency;
  }

  public BloodType[] getBloodTypes() {
    return bloodTypes;
  }

  public void setBloodTypes(BloodType[] bloodTypes) {
    this.bloodTypes = bloodTypes;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public BedPackage[] getPackages() {
    return packages;
  }

  public void setPackages(BedPackage[] packages) {
    this.packages = packages;
  }

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
      throw new IllegalArgumentException("Invalid bed type");
    }

    @Override
    public String toString() {
      return this.label;
    }
  }

  public enum CleaningFrequency {
    @JsonProperty("weekly")
    WEEKLY("weekly"),
    @JsonProperty("monthly")
    MONTHLY("monthly"),
    @JsonProperty("annual")
    ANNUAL("annual"),
    @JsonProperty("never")
    NEVER("never");

    private String label;

    CleaningFrequency(String frequency) {
      this.label = frequency;
    }

    public static CleaningFrequency valueOfLabel(String frequency) {
      for (CleaningFrequency cleaningFrequency : values()) {
        if (cleaningFrequency.label.equals(frequency)) {
          return cleaningFrequency;
        }
      }
      throw new IllegalArgumentException("Invalid cleaning frequency");
    }

    @Override
    public String toString() {
      return this.label;
    }
  }

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

  public static class BedPackage {
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

  public Bed() {}

  public Bed(
      String ownerPublicKey,
      String zipCode,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int capacity,
      BedPackage[] packages) {

    this.setOwnerPublicKey(ownerPublicKey);
    this.setZipCode(zipCode);
    this.setBedType(bedType);
    this.setCleaningFrequency(cleaningFrequency);
    this.setBloodTypes(bloodTypes);
    this.setCapacity(capacity);
    this.setPackages(packages);
  }
}
