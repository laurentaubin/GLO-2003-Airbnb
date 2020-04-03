package application;

import domain.bed.enums.*;
import domain.bed.exception.*;
import domain.booking.exception.InvalidArrivalDateException;
import domain.booking.exception.InvalidNumberOfNightsException;
import domain.geolocation.Geolocation;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Query {
  private PackageName[] packageNames;
  private BedType[] bedTypes;
  private CleaningFrequency[] cleaningFrequencies;
  private BloodType[] bloodTypes;
  private int minCapacity;
  private LodgingMode[] lodgingModes;
  private int numberOfNights;
  private String arrivalDate;
  private String origin;
  private double maxDistance;
  private boolean originGiven;
  private Point2D coordinates;

  public Query(
      String packageNames,
      String bedTypes,
      String cleaningFrequencies,
      String bloodtypes,
      String minCapacity,
      String lodgingModes,
      String arrivalDate,
      String numberOfNights,
      String origin,
      String maxDistance) {

    validateIfArrivalDateSpecifiedMinCapacityIsSpecified(arrivalDate, minCapacity);
    validateIfNumberOfNightsSpecifiedMinCapacityIsSpecified(numberOfNights, minCapacity);
    validateIfMaxDistanceSpecifiedOriginIsSpecified(maxDistance, origin);

    this.packageNames = unrollPackages(packageNames);
    this.bedTypes = unrollBedTypes(bedTypes);
    this.cleaningFrequencies = unrollCleaningFrequencies(cleaningFrequencies);
    this.bloodTypes = unrollBloodTypes(bloodtypes);
    this.minCapacity = unrollMinCapacity(minCapacity);
    this.lodgingModes = unrollLodgingModes(lodgingModes);
    this.arrivalDate = unrollArrivalDate(arrivalDate);
    this.numberOfNights = unrollNumberOfNights(numberOfNights);
    this.origin = unrollOrigin(origin);
    this.maxDistance = unrollMaxDistance(maxDistance);
  }

  private void validateIfMaxDistanceSpecifiedOriginIsSpecified(String maxDistance, String origin) {
    if (!maxDistance.equals("empty")) {
      if (origin.equals("empty")) {
        throw new InvalidMaxDistanceWithoutOriginException();
      }
    }
  }

  private void validateIfArrivalDateSpecifiedMinCapacityIsSpecified(
      String arrivalDate, String minCapacity) {
    if (!arrivalDate.equals("empty")) {
      if (minCapacity.equals("empty")) {
        throw new InvalidDateWithoutMinCapacityException();
      }
    }
  }

  private void validateIfNumberOfNightsSpecifiedMinCapacityIsSpecified(
      String numberOfNights, String minCapacity) {
    if (!numberOfNights.equals("empty")) {
      if (minCapacity.equals("empty")) {
        throw new InvalidNumberOfNightsWithoutMinCapacityException();
      }
    }
  }

  private String unrollOrigin(String origin) {
    if (origin.equals("empty")) {
      this.originGiven = false;
      return origin;
    }

    if (Geolocation.validateZipCode(origin)) {
      this.coordinates = Geolocation.getZipCodeCoordinates(origin);
      this.originGiven = true;
    }

    return origin;
  }

  private double unrollMaxDistance(String maxDistance) {
    if (maxDistance.equals("empty")) {
      return 10.0;
    }

    try {
      double distance = Double.parseDouble(maxDistance);
    } catch (NumberFormatException e) {
      throw new InvalidMaxDistanceException();
    }

    if (Double.parseDouble(maxDistance) <= 0) {
      throw new InvalidMaxDistanceException();
    }

    return Double.parseDouble(maxDistance);
  }

  private int unrollMinCapacity(String minCapacity) {
    if (minCapacity.equals("empty")) {
      return 1;
    }

    try {
      int capacity = Integer.parseInt(minCapacity);
    } catch (NumberFormatException e) {
      throw new InvalidMinCapacityException();
    }

    if (Integer.parseInt(minCapacity) <= 0) {
      throw new InvalidMinCapacityException();
    }

    return Integer.parseInt(minCapacity);
  }

  private String unrollArrivalDate(String arrivalDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    if (arrivalDate.equals("empty")) {
      return LocalDate.now().format(formatter);
    }

    try {
      LocalDate date = LocalDate.parse(arrivalDate, formatter);
    } catch (DateTimeParseException e) {
      throw new InvalidArrivalDateException();
    }

    if (isArrivalDateInThePast(arrivalDate)) {
      throw new ArrivalDateInPastException();
    }

    return arrivalDate;
  }

  private boolean isArrivalDateInThePast(String arrivalDate) {
    LocalDate currentDate = LocalDate.now();
    LocalDate dateOfArrival = LocalDate.parse(arrivalDate);
    int thisDateGreaterThanOtherDate = dateOfArrival.compareTo(currentDate);
    return thisDateGreaterThanOtherDate < 0;
  }

  private int unrollNumberOfNights(String numberOfNights) {
    if (numberOfNights.equals("empty")) {
      return 3;
    }

    try {
      int nbNights = Integer.parseInt(numberOfNights);
    } catch (NumberFormatException e) {
      throw new InvalidNumberOfNightsException();
    }

    if (Integer.parseInt(numberOfNights) < 1 || Integer.parseInt(numberOfNights) > 90) {
      throw new InvalidNumberOfNightsException();
    }

    return Integer.parseInt(numberOfNights);
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

  private LodgingMode[] unrollLodgingModes(String _lodgingModes) {
    LodgingMode[] lodgingModes;
    if (_lodgingModes.equals("empty")) {
      lodgingModes = new LodgingMode[] {LodgingMode.PRIVATE, LodgingMode.COHABITATION};
    } else {
      lodgingModes = new LodgingMode[] {LodgingMode.valueOfLabel(_lodgingModes)};
    }
    return lodgingModes;
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

  public LodgingMode[] getLodgingModes() {
    return lodgingModes;
  }

  public int getNumberOfNights() {
    return numberOfNights;
  }

  public String getArrivalDate() {
    return arrivalDate;
  }

  public boolean isOriginGiven() {
    return originGiven;
  }

  public double getMaxDistance() {
    return maxDistance;
  }

  public Point2D getCoordinates() {
    return coordinates;
  }
}
