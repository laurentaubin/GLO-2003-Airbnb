package bed;

public class Query {
  private PackageName packageName;
  private BedType bedType;
  private CleaningFrequency cleaningFrequency;
  private BloodType[] bloodTypes;
  private int minCapacity;

  public Query(
      PackageName packageName,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int minCapacity) {
    this.packageName = packageName;
    this.bedType = bedType;
    this.cleaningFrequency = cleaningFrequency;
    this.bloodTypes = bloodTypes;
    this.minCapacity = minCapacity;
  }

  public int getMinCapacity() {
    return minCapacity;
  }

  public PackageName getPackageName() {
    return packageName;
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
