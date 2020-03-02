package bed;

public class BedsResponse {
  private String bedNumber;
  private String zipCode;
  private BedType bedType;
  private CleaningFrequency cleaningFrequency;
  private BloodType[] bloodTypes;
  private int capacity;
  private BedPackage[] packages;
  private int stars;

  public BedsResponse(
      String bedNumber,
      String zipCode,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int capacity,
      int stars,
      BedPackage[] packages) {
    this.bedNumber = bedNumber;
    this.zipCode = zipCode;
    this.bedType = bedType;
    this.cleaningFrequency = cleaningFrequency;
    this.bloodTypes = bloodTypes;
    this.capacity = capacity;
    this.stars = stars;
    this.packages = packages;
  }

  public CleaningFrequency getCleaningFrequency() {
    return cleaningFrequency;
  }

  public BloodType[] getBloodTypes() {
    return bloodTypes;
  }

  public BedType getBedType() {
    return bedType;
  }

  public BedPackage[] getPackages() {
    return packages;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getStars() {
    return stars;
  }

  public String getBedNumber() {
    return bedNumber;
  }

  public String getZipCode() {
    return zipCode;
  }
}
