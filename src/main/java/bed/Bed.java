package bed;

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

  public double getCleaningFrequencyScore() {
    switch (this.cleaningFrequency) {
      case WEEKLY:
        return 0.5;
      case MONTHLY:
        return 1;
      case ANNUAL:
        return 1.25;
      case NEVER:
        return 2;
    }
    throw new IllegalArgumentException("Invalid cleaning frequency type");
  }

  public int getMattressScore() {
    switch (this.bedType) {
      case LATEX:
        return 250;
      case MEMORY_FOAM:
        return 500;
      case SPRINGS:
        return 750;
    }
    throw new IllegalArgumentException("Invalid blood type string");
  }

  public double getBloodTypeScore() {
    double scoreBloodType = 0;
    for (int i = 0; i < this.bloodTypes.length; i++) {
      switch (this.bloodTypes[i]) {
        case O_NEG:
          scoreBloodType += 1.5;
          break;
        case O_POS:
          scoreBloodType += 1;
          break;
        case A_NEG:
          scoreBloodType += 0.6;
          break;
        case A_POS:
          scoreBloodType += 0.5;
          break;
        case B_NEG:
          scoreBloodType += 0.5;
          break;
        case B_POS:
          scoreBloodType += 0.4;
          break;
        case AB_NEG:
          scoreBloodType += 0.2;
          break;
        case AB_POS:
          scoreBloodType += 0.1;
          break;
        default:
          throw new IllegalArgumentException("Invalid blood type");
      }
    }
    return scoreBloodType / (this.bloodTypes.length);
  }

  public int getNomberOfStars() {
    double globalScore = getMattressScore() * getCleaningFrequencyScore() * getBloodTypeScore();
    if (0 <= globalScore && globalScore < 100) {
      return 1;
    } else if (100 <= globalScore && globalScore < 187.5) {
      return 2;
    } else if (187.5 <= globalScore && globalScore < 300) {
      return 3;
    } else if (300 <= globalScore && globalScore < 500) {
      return 4;
    } else {
      return 5;
    }
  }

  public enum BedType {
    LATEX("latex"),
    MEMORY_FOAM("memory-foam"),
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
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    ANNUAL("annual"),
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
      BLOOD_THIRSTY,
      ALL_YOU_CAN_DRINK,
      SWEET_TOOTH
    }

    public BedPackage(Name name, double pricePerNight) {
      this.setName(name);
      this.setPricePerNight(pricePerNight);
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
