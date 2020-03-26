package domain.bed;

import static org.junit.jupiter.api.Assertions.*;

import domain.bed.enums.*;
import domain.booking.Booking;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class BedTest {

  @Test
  void creatingBed_withValidParameters_shouldNotThrow() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.LATEX;
    CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
    BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
    int capacity = 950;
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(PackageName.BLOOD_THIRSTY, 12.5),
          new BedPackage(PackageName.SWEET_TOOTH, 6)
        };

    assertDoesNotThrow(
        () ->
            new Bed(
                ownerPublicKey,
                zipCode,
                bedType,
                cleaningFrequency,
                bloodTypes,
                capacity,
                packages));
  }

  @Test
  void creatingBed_withoutSpecifyingLodgingMode_shouldDefaultToPrivateMode() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.LATEX;
    CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
    BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
    int capacity = 950;
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(PackageName.BLOOD_THIRSTY, 12.5),
          new BedPackage(PackageName.SWEET_TOOTH, 6)
        };

    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
    assertEquals(LodgingMode.PRIVATE, bedTest.getLodgingMode());
  }

  @Test
  void findIntersections_bookingAndQueryHaveSameDates_shouldReturnCompleteList() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;

    Booking booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);

    ArrayList<String> initialDates =
        new ArrayList<>(Arrays.asList("2020-05-21", "2020-05-22", "2020-05-23"));

    ArrayList<String> intersections = Bed.intersections(booking, arrivalDate, numberOfNights);
    Collections.sort(intersections);

    assertEquals(initialDates, intersections);
  }

  @Test
  void findIntersections_bookingOverlapsBefore_shouldReturnIntersectingDates() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    String bookingArrivalDate = "2020-05-19";
    int bookingNumberOfNights = 4;
    Booking booking =
        new Booking(tenantPublicKey, bookingArrivalDate, bookingNumberOfNights, bedPackage);

    String queryArrivalDate = "2020-05-21";
    int queryNumberOfNights = 3;

    ArrayList<String> initialDates = new ArrayList<>(Arrays.asList("2020-05-21", "2020-05-22"));

    ArrayList<String> intersections =
        Bed.intersections(booking, queryArrivalDate, queryNumberOfNights);
    Collections.sort(intersections);

    assertEquals(initialDates, intersections);
  }

  @Test
  void findIntersections_bookingOverlapsAfter_shouldReturnIntersectingDates() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    String arrivalDate = "2020-05-22";
    int numberOfNights = 3;
    Booking booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);

    String queryArrivalDate = "2020-05-21";
    int queryNumberOfNights = 3;

    ArrayList<String> initialDates = new ArrayList<>(Arrays.asList("2020-05-22", "2020-05-23"));

    ArrayList<String> intersections =
        Bed.intersections(booking, queryArrivalDate, queryNumberOfNights);
    Collections.sort(intersections);

    assertEquals(initialDates, intersections);
  }

  @Test
  void findIntersections_bookingContainedInsideQueryDates_bookingDates() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    Booking booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);

    String queryArrivalDate = "2020-05-15";
    int queryNumberOfNights = 14;

    ArrayList<String> initialDates =
        new ArrayList<>(Arrays.asList("2020-05-21", "2020-05-22", "2020-05-23"));

    ArrayList<String> intersections =
        Bed.intersections(booking, queryArrivalDate, queryNumberOfNights);
    Collections.sort(intersections);

    assertEquals(initialDates, intersections);
  }

  @Test
  void findIntersections_bookingBefore_shouldReturnEmptyList() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    String arrivalDate = "2020-05-15";
    int numberOfNights = 3;
    Booking booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);

    String queryArrivalDate = "2020-05-21";
    int queryNumberOfNights = 3;

    ArrayList<String> initialDates = new ArrayList<>();

    ArrayList<String> intersections =
        Bed.intersections(booking, queryArrivalDate, queryNumberOfNights);
    Collections.sort(intersections);

    assertEquals(initialDates, intersections);
  }

  @Test
  void findIntersections_bookingAfter_shouldReturnEmptyList() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    String arrivalDate = "2020-05-25";
    int numberOfNights = 3;
    Booking booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);

    String queryArrivalDate = "2020-05-21";
    int queryNumberOfNights = 3;

    ArrayList<String> initialDates = new ArrayList<>();

    ArrayList<String> intersections =
        Bed.intersections(booking, queryArrivalDate, queryNumberOfNights);
    Collections.sort(intersections);

    assertEquals(initialDates, intersections);
  }

  @Test
  void isBedAvailable_noOverlappingBookings_shouldReturnTrue() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 200;
    Bed bed = createBed(bedCapacity);

    assertTrue(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  @Test
  void isBedAvailable_freePartlyButStillRoom_shouldReturnTrue() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 200;
    Bed bed = createBed(bedCapacity);

    String bookingArrivalDate = "2020-05-19";
    int bookingNumberOfNights = 2;
    int bookingColonySize = 75;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed.addBooking(booking);

    assertTrue(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  @Test
  void isBedAvailable_peopleEverywhereButStillRoom_shouldReturnTrue() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 200;
    Bed bed = createBed(bedCapacity);

    String bookingArrivalDate = "2020-05-21";
    int bookingNumberOfNights = 3;
    int bookingColonySize = 75;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed.addBooking(booking);

    assertTrue(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  @Test
  void isBedAvailable_oneBookingTakesTheWholePlace_shouldReturnFalse() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 200;
    Bed bed = createBed(bedCapacity);

    String bookingArrivalDate = "2020-05-21";
    int bookingNumberOfNights = 3;
    int bookingColonySize = 175;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed.addBooking(booking);

    assertFalse(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  @Test
  void isBedAvailable_twoSmallBookingsOnSameDatesFillRoom_shouldReturnFalse() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 200;
    Bed bed = createBed(bedCapacity);

    String bookingArrivalDate1 = "2020-05-21";
    int bookingNumberOfNights1 = 3;
    int bookingColonySize1 = 75;
    Booking booking1 =
        createBooking(bookingArrivalDate1, bookingNumberOfNights1, bookingColonySize1);
    bed.addBooking(booking1);

    String bookingArrivalDate2 = "2020-05-21";
    int bookingNumberOfNights2 = 3;
    int bookingColonySize2 = 75;
    Booking booking2 =
        createBooking(bookingArrivalDate2, bookingNumberOfNights2, bookingColonySize2);
    bed.addBooking(booking2);

    assertFalse(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  @Test
  void isBedAvailable_otherBookingBlockingStartDates_shouldReturnFalse() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 200;
    Bed bed = createBed(bedCapacity);

    String bookingArrivalDate = "2020-05-19";
    int bookingNumberOfNights = 3;
    int bookingColonySize = 200;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed.addBooking(booking);

    assertFalse(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  @Test
  void isBedAvailable_otherBookingBlockingEndDates_shouldReturnFalse() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 200;
    Bed bed = createBed(bedCapacity);

    String bookingArrivalDate = "2020-05-22";
    int bookingNumberOfNights = 3;
    int bookingColonySize = 200;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed.addBooking(booking);

    assertFalse(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  @Test
  void isBedAvailable_emptyButCapacityIsTooLow_shouldReturnFalse() {
    String arrivalDate = "2020-05-21";
    int numberOfNights = 3;
    int minCapacity = 100;

    int bedCapacity = 50;
    Bed bed = createBed(bedCapacity);

    assertFalse(bed.isAvailable(arrivalDate, numberOfNights, minCapacity));
  }

  private Bed createBed(int capacity) {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.LATEX;
    CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
    BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(PackageName.BLOOD_THIRSTY, 12.5),
          new BedPackage(PackageName.SWEET_TOOTH, 6)
        };
    return new Bed(
        ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
  }

  private Booking createBooking(String arrivalDate, int numberOfNights, int colonySize) {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    Booking booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);
    booking.setColonySize(colonySize);
    return booking;
  }
}
