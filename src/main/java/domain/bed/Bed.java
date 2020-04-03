package domain.bed;

import domain.bed.enums.*;
import domain.booking.Booking;
import domain.geolocation.Geolocation;
import infrastructure.booking.BookingRepository;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
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
  private BookingRepository bookingRepository = new BookingRepository();
  private LodgingMode lodgingMode;
  private Point2D coordinates;

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
    this.setCoordinates(Geolocation.getZipCodeCoordinates(this.zipCode));
  }

  public Bed(
      String ownerPublicKey,
      String zipCode,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int capacity,
      BedPackage[] packages,
      LodgingMode lodgingMode) {

    this.setOwnerPublicKey(ownerPublicKey);
    this.setZipCode(zipCode);
    this.setBedType(bedType);
    this.setCleaningFrequency(cleaningFrequency);
    this.setBloodTypes(bloodTypes);
    this.setCapacity(capacity);
    this.setPackages(packages);
    this.setLodgingMode(lodgingMode);
  }

  public Point2D getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Point2D coordinates) {
    this.coordinates = coordinates;
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

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String addBooking(Booking booking) {
    String bookingUuid = UUID.randomUUID().toString();
    this.bookingRepository.addBooking(bookingUuid, booking);
    return bookingUuid;
  }

  public Booking getBookingByUuid(String uuid) {
    return this.bookingRepository.getBooking(uuid);
  }

  public ArrayList<Booking> getAllBookings() {
    return this.bookingRepository.getAllBookings();
  }

  public LodgingMode getLodgingMode() {
    return lodgingMode;
  }

  public void setLodgingMode(LodgingMode lodgingMode) {
    this.lodgingMode = lodgingMode;
  }

  public void cancelBooking(String bookingUuid) {
    this.getBookingByUuid(bookingUuid).cancelBooking();
  }

  public static ArrayList<String> intersections(
      Booking booking, String arrivalDate, int numberOfNights) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Set<String> bookingDates =
        getStayDates(formatter, booking.getArrivalDate(), booking.getNumberOfNights());

    Set<String> queryDates = getStayDates(formatter, arrivalDate, numberOfNights);

    queryDates.retainAll(bookingDates);

    return new ArrayList<>(queryDates);
  }

  private static Set<String> getStayDates(
      DateTimeFormatter formatter, String arrivalDate2, int numberOfNights2) {
    Set<String> bookingDates = new HashSet<>();
    LocalDate bookingStartDate = LocalDate.parse(arrivalDate2);
    for (int i = 0; i < numberOfNights2; i++) {
      LocalDate bookingDate = bookingStartDate.plusDays(i);
      bookingDates.add(bookingDate.format(formatter));
    }
    return bookingDates;
  }

  public boolean isAvailable(String arrivalDate, int numberOfNights, int minCapacity) {
    HashMap<String, Integer> stayDates = initializeStayDates(arrivalDate, numberOfNights);

    updateStayDatesCapacityWithBookings(arrivalDate, numberOfNights, stayDates);

    for (Integer peopleInRoom : stayDates.values()) {
      if (noRoomInBed(minCapacity, peopleInRoom)) {
        return false;
      }
    }
    return true;
  }

  private boolean noRoomInBed(int minCapacity, Integer peopleInRoom) {
    return minCapacity > this.getCapacity() - peopleInRoom;
  }

  private void updateStayDatesCapacityWithBookings(
      String arrivalDate, int numberOfNights, HashMap<String, Integer> stayDates) {
    for (Booking booking : getAllBookings()) {
      ArrayList<String> intersections = Bed.intersections(booking, arrivalDate, numberOfNights);
      if (intersections.size() != 0) {
        for (String date : intersections) {
          int oldCapacity = stayDates.get(date);
          stayDates.replace(date, oldCapacity + booking.getColonySize());
        }
      }
    }
  }

  private HashMap<String, Integer> initializeStayDates(String arrivalDate, int numberOfNights) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    HashMap<String, Integer> stayDates = new HashMap<String, Integer>();
    LocalDate startDate = LocalDate.parse(arrivalDate);
    for (int i = 0; i < numberOfNights; i++) {
      LocalDate newDate = startDate.plusDays(i);
      stayDates.put(newDate.format(formatter), 0);
    }
    return stayDates;
  }
}
