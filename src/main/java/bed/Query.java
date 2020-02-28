package bed;

public class Query {
  private BedPackage bedPackage;
  private BedType bedType;
  private CleaningFrequency cleaningFrequency;
  private BloodType[] bloodTypes;
  private int minCapacity;

  public Query(
      BedPackage bedPackage,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int minCapacity) {
    this.bedPackage = bedPackage;
    this.bedType = bedType;
    this.cleaningFrequency = cleaningFrequency;
    this.bloodTypes = bloodTypes;
    this.minCapacity = minCapacity;
  }

  public int getMinCapacity() {
    return minCapacity;
  }

  public BedPackage getBedPackage() {
    return bedPackage;
  }

  public BedType getBedType() {
    return bedType;
  }

  public BloodType[] getBloodTypes() {
    return bloodTypes;
  }

  public CleaningFrequency getCleaningFrequency() {
    return cleaningFrequency;
  }
}
