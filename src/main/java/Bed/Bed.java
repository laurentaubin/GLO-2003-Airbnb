package Bed;

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
    LATEX,
    MEMORYFOAM,
    SPRINGS
  }

  public enum CleaningFrequency {
    WEEKLY,
    MONTHLY,
    ANNUAL,
    NEVER
  }

  public enum BloodType {
    ONEG,
    OPOS,
    ANEG,
    APOS,
    BNEG,
    BPOS,
    ABNEG,
    ABPOS;

    public static BloodType getBloodTypeFromString(String bloodTypeString) {
      switch (bloodTypeString) {
        case "O-":
          return ONEG;
        case "O+":
          return OPOS;
        case "A-":
          return ANEG;
        case "A+":
          return APOS;
        case "B-":
          return BNEG;
        case "B+":
          return BPOS;
        case "AB-":
          return ABNEG;
        case "AB+":
          return ABPOS;
      }
      throw new IllegalArgumentException("Invalid blood type string");
    }
  }

  public static class BedPackage {
    private Name name;
    private double pricePerNight;

    public enum Name {
      BLOODTHIRSTY,
      ALLYOUCANDRINK,
      SWEETTOOTH
    }

    public BedPackage(Name name, double pricePerNight) {}
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

    setOwnerPublicKey(ownerPublicKey);
    setZipCode(zipCode);
    setBedType(bedType);
    setCleaningFrequency(cleaningFrequency);
    setBloodTypes(bloodTypes);
    setCapacity(capacity);
    setPackages(packages);
  }
}
