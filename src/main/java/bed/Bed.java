package bed;

import bed.booking.Booking;
import bed.booking.exception.BookingNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bed {
  private String ownerPublicKey;
  private String zipCode;
  private BedType bedType;
  private CleaningFrequency cleaningFrequency;
  private BloodType[] bloodTypes;
  private int capacity;
  private BedPackage[] packages;
  private String uuid;
  private Map<String, Booking> bookings = new HashMap<>();
  private LodgingMode lodgingMode;

  public Bed() {
    this.setLodgingMode(LodgingMode.PRIVATE);
  }

  public Bed(
      String ownerPublicKey,
      String zipCode,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int capacity,
      BedPackage[] packages) {

    this.setOwnerPublicKey(ownerPublicKey);
    this.setZipCode(zipCode);
    this.setBedType(bedType);
    this.setCleaningFrequency(cleaningFrequency);
    this.setBloodTypes(bloodTypes);
    this.setCapacity(capacity);
    this.setPackages(packages);
    this.setLodgingMode(LodgingMode.PRIVATE);
  }

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

  public PackageName[] packagesNames() {
    ArrayList<PackageName> packageNamesList = new ArrayList<>();
    for (BedPackage bedPackage : getPackages()) {
      packageNamesList.add(bedPackage.getName());
    }
    return packageNamesList.toArray(new PackageName[packageNamesList.size()]);
  }

  public void setPackages(BedPackage[] packages) {
    this.packages = packages;
  }

  private double getGlobalBloodTypeScore() {
    double bloodTypeScore = 0;
    for (BloodType bloodType : this.bloodTypes) {
      bloodTypeScore += bloodType.getScore();
    }
    return (bloodTypeScore / this.bloodTypes.length);
  }

  public int getStars() {
    try {
      double globalScore =
          this.cleaningFrequency.getScore() * this.bedType.getScore() * getGlobalBloodTypeScore();
      if (0 <= globalScore && globalScore < 100) {
        return 1;
      } else if (100 <= globalScore && globalScore < 187.5) {
        return 2;
      } else if (187.5 <= globalScore && globalScore < 300) {
        return 3;
      } else if (300 <= globalScore && globalScore < 500) {
        return 4;
      } else {
        return 5;
      }
    } catch (Exception e) {
      return -1;
    }
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String addBooking(Booking booking) {
    String bookingUuid = UUID.randomUUID().toString();
    bookings.put(bookingUuid, booking);
    return bookingUuid;
  }

  public Booking getBookingByUuid(String uuid) {
    if (!bookings.containsKey(uuid)) {
      throw new BookingNotFoundException(uuid);
    }
    return bookings.get(uuid);
  }

  public ArrayList<Booking> getAllBookings() {
    return new ArrayList<>(bookings.values());
  }

  public LodgingMode getLodgingMode() {
    return lodgingMode;
  }

  public void setLodgingMode(LodgingMode lodgingMode) {
    this.lodgingMode = lodgingMode;
  }
}
