package presentation.bed;

import domain.bed.enums.*;
import java.util.ArrayList;

public class BedResponse {
  private String bedNumber;
  private String zipCode;
  private String bedType;
  private String cleaningFrequency;
  private ArrayList<String> bloodTypes;
  private int capacity;
  private ArrayList<BedPackageResponse> packages;
  private int stars;
  private String lodgingMode;

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
    this.bedType = setBedTypeAsString(bedType);
    this.cleaningFrequency = setCleaningFrequencyAsString(cleaningFrequency);
    this.bloodTypes = this.setBloodTypesAsString(bloodTypes);
    this.capacity = capacity;
    this.stars = stars;
    this.packages = setBedPackageAsString(packages);
    this.lodgingMode = setLodgingModeAsString(lodgingMode);
  }

  public String setBedTypeAsString(BedType bedtype) {
    return bedtype.toString();
  }

  public ArrayList<String> setBloodTypesAsString(BloodType[] bloodTypes) {
    ArrayList<String> bloodtypesName = new ArrayList<>();
    for (BloodType bloodType : bloodTypes) {
      bloodtypesName.add(bloodType.toString());
    }
    return bloodtypesName;
  }

  public String setCleaningFrequencyAsString(CleaningFrequency cleaningFrequency) {
    return cleaningFrequency.toString();
  }

  public ArrayList<BedPackageResponse> setBedPackageAsString(BedPackage[] packages) {
    ArrayList<BedPackageResponse> bedPackageResponses = new ArrayList<>();
    for (BedPackage bedPackage : packages) {
      bedPackageResponses.add(new BedPackageResponse(bedPackage));
    }
    return bedPackageResponses;
  }

  public String setLodgingModeAsString(LodgingMode lodgingMode) {
    return lodgingMode.toString();
  }

  public String getCleaningFrequency() {
    return cleaningFrequency;
  }

  public ArrayList<String> getBloodTypes() {
    return bloodTypes;
  }

  public String getBedType() {
    return bedType;
  }

  public ArrayList<BedPackageResponse> getPackages() {
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

  public String getLodgingMode() {
    return lodgingMode;
  }
}
