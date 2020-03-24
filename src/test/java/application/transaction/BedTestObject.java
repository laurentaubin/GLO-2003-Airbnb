package application.transaction;

import domain.bed.Bed;
import domain.bed.enums.BedPackage;
import domain.bed.enums.BedType;
import domain.bed.enums.BloodType;
import domain.bed.enums.CleaningFrequency;
import domain.bed.enums.PackageName;

public class BedTestObject {
  private String ownerKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  private String zipCode = "12345";
  private BedType bedType = BedType.LATEX;
  private CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
  private BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
  private int capacity = 950;
  private BedPackage[] packages =
      new BedPackage[] {
        new BedPackage(PackageName.BLOOD_THIRSTY, 12.5), new BedPackage(PackageName.SWEET_TOOTH, 6)
      };

  public BedTestObject() {}

  public Bed getBed() {
    return new Bed(ownerKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
  }
}
