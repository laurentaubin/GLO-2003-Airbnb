package presentation.bed;

import domain.bed.enums.*;

public class BedResponse {
  private String bedNumber;
  private String zipCode;
  private BedType bedType;
  private CleaningFrequency cleaningFrequency;
  private BloodType[] bloodTypes;
  private int capacity;
  private BedPackage[] packages;
  private int stars;
  private LodgingMode lodgingMode;

  public BedResponse(
      String bedNumber,
      String zipCode,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int capacity,
      int stars,
      BedPackage[] packages,
      LodgingMode lodgingMode) {
    this.bedNumber = bedNumber;
    this.zipCode = zipCode;
    this.bedType = bedType;
    this.cleaningFrequency = cleaningFrequency;
    this.bloodTypes = bloodTypes;
    this.capacity = capacity;
    this.stars = stars;
    this.packages = packages;
    this.lodgingMode = lodgingMode;
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

  public LodgingMode getLodgingMode() {
    return lodgingMode;
  }
}
