package bed;

import java.util.ArrayList;

public class Query {
  private PackageName[] packageNames;
  private BedType[] bedTypes;
  private CleaningFrequency[] cleaningFrequencies;
  private BloodType[] bloodTypes;
  private int minCapacity;

  public Query(
      String packageNames,
      String bedTypes,
      String cleaningFrequencies,
      String bloodtypes,
      String minCapacity) {
    this.packageNames = unrollPackages(packageNames);
    this.bedTypes = unrollBedTypes(bedTypes);
    this.cleaningFrequencies = unrollCleaningFrequencies(cleaningFrequencies);
    this.bloodTypes = unrollBloodTypes(bloodtypes);
    this.minCapacity = unrollMinCapacity(minCapacity);
  }

  private int unrollMinCapacity(String minCapacity) {
    return Integer.parseInt(minCapacity);
  }

  private BloodType[] unrollBloodTypes(String _bloodTypes) {
    BloodType[] bloodTypes;
    if (_bloodTypes.equals("empty")) {
      bloodTypes = new BloodType[0];
    } else {
      String[] bloodTypesStrings = _bloodTypes.split(",");
      ArrayList<BloodType> bloodTypesList = new ArrayList<>();
      for (String bloodType : bloodTypesStrings) {
        if (bloodType.charAt(bloodType.length() - 1) == ' ') {
          bloodType = bloodType.substring(0, bloodType.length() - 1);
          bloodTypesList.add(BloodType.valueOfLabel(bloodType + "+"));
        } else {
          bloodTypesList.add(BloodType.valueOfLabel(bloodType));
        }
      }
      bloodTypes = bloodTypesList.toArray(new BloodType[bloodTypesStrings.length]);
    }
    return bloodTypes;
  }

  private CleaningFrequency[] unrollCleaningFrequencies(String _cleaningFrequencies) {
    CleaningFrequency[] cleaningFrequencies;
    if (_cleaningFrequencies.equals("empty")) {
      cleaningFrequencies =
          new CleaningFrequency[] {
            CleaningFrequency.ANNUAL,
            CleaningFrequency.MONTHLY,
            CleaningFrequency.WEEKLY,
            CleaningFrequency.NEVER
          };
    } else {
      cleaningFrequencies =
          new CleaningFrequency[] {CleaningFrequency.valueOfLabel(_cleaningFrequencies)};
    }
    return cleaningFrequencies;
  }

  private BedType[] unrollBedTypes(String _bedTypes) {
    BedType[] bedTypes;
    if (_bedTypes.equals("empty")) {
      bedTypes = new BedType[] {BedType.LATEX, BedType.MEMORY_FOAM, BedType.SPRINGS};
    } else {
      bedTypes = new BedType[] {BedType.valueOfLabel(_bedTypes)};
    }
    return bedTypes;
  }

  private PackageName[] unrollPackages(String _packageNames) {
    PackageName[] packageNames;
    if (_packageNames.equals("empty")) {
      packageNames =
          new PackageName[] {
            PackageName.ALL_YOU_CAN_DRINK, PackageName.BLOOD_THIRSTY, PackageName.SWEET_TOOTH
          };
    } else {
      packageNames = new PackageName[] {PackageName.valueOfLabel(_packageNames)};
    }
    return packageNames;
  }

  public int getMinCapacity() {
    return minCapacity;
  }

  public PackageName[] getPackagesNames() {
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
