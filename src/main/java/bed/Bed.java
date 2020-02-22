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

  private double getGlobalBloodTypeScore() {
    double bloodTypeScore = 0;
    for (BloodType bloodType : this.bloodTypes) {
      bloodTypeScore += bloodType.getScore();
    }
    return (bloodTypeScore / this.bloodTypes.length);
  }

  public int getNomberOfStars() {
    double globalScore = this.cleaningFrequency.getScore() * this.bedType.getScore() * getGlobalBloodTypeScore();
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
    LATEX("latex", 250),
    MEMORY_FOAM("memory-foam", 500),
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
      throw new IllegalArgumentException("Invalid bed type");
    }

    public int getScore() {
      return this.score;
    }

    @Override
    public String toString() {
      return this.label;
    }
  }

  public enum CleaningFrequency {
    WEEKLY("weekly", 0.5),
    MONTHLY("monthly", 1),
    ANNUAL("annual", 1.25),
    NEVER("never", 2);

    private String label;
    private double score;

    CleaningFrequency(String frequency, double score) {
      this.label = frequency;
      this.score = score;
    }

    public static CleaningFrequency valueOfLabel(String frequency) {
      for (CleaningFrequency cleaningFrequency : values()) {
        if (cleaningFrequency.label.equals(frequency)) {
          return cleaningFrequency;
        }
      }
      throw new IllegalArgumentException("Invalid cleaning frequency");
    }

    public double getScore (){
      return this.score;
    }

    @Override
    public String toString() {
      return this.label;
    }
  }

  public enum BloodType {
    O_NEG("O-", 1.5),
    O_POS("O+", 1),
    A_NEG("A-", 0.6),
    A_POS("A+", 0.5),
    B_NEG("B-", 0.5),
    B_POS("B+", 0.4),
    AB_NEG("AB-", 0.2),
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
      throw new IllegalArgumentException("Invalid blood type");
    }

    public double getScore() {
      return this.score;
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
