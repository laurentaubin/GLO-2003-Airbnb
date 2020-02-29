package bed;

public class Query {
  private PackageName[] packageNames;
  private BedType[] bedTypes;
  private CleaningFrequency[] cleaningFrequencies;
  private BloodType[] bloodTypes;
  private int minCapacity;

  public Query(
      PackageName[] packageNames,
      BedType[] bedTypes,
      CleaningFrequency[] cleaningFrequencies,
      BloodType[] bloodTypes,
      int minCapacity) {
    this.packageNames = packageNames;
    this.bedTypes = bedTypes;
    this.cleaningFrequencies = cleaningFrequencies;
    this.bloodTypes = bloodTypes;
    this.minCapacity = minCapacity;
  }

  public int getMinCapacity() {
    return minCapacity;
  }

  public PackageName[] getPackageNames() {
    return packageNames;
  }

  public BedType[] getBedTypes() {
    return bedTypes;
  }

  public BloodType[] getBloodTypes() {
    return bloodTypes;
  }

  public CleaningFrequency[] getCleaningFrequencies() {
    return cleaningFrequencies;
  }
}
