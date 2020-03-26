package application.bed;

import static org.junit.jupiter.api.Assertions.*;

import application.Query;
import domain.bed.*;
import domain.bed.enums.*;
import domain.booking.Booking;
import domain.booking.BookingStatus;
import domain.booking.exception.BedNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedServiceTest {
  private BedService bedService = BedService.getInstance();
  String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  String zipCode = "12345";
  BedType bedType = BedType.LATEX;
  CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
  BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
  int capacity = 950;
  BedPackage[] packages =
      new BedPackage[] {
        new BedPackage(PackageName.BLOOD_THIRSTY, 12.5), new BedPackage(PackageName.SWEET_TOOTH, 6)
      };
  private Bed bed;
  private String bedUuid = UUID.randomUUID().toString();

  @BeforeEach
  void setUp() {
    this.bed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages);
  }

  @AfterEach
  void tearDown() {
    bedService.clearAllBeds();
  }

  private void createBeds() {
    ArrayList<Bed> beds = new ArrayList<>();

    BloodType[] bloodTypes1 =
        new BloodType[] {
          BloodType.A_NEG,
          BloodType.A_POS,
          BloodType.AB_NEG,
          BloodType.AB_POS,
          BloodType.B_NEG,
          BloodType.B_POS,
          BloodType.O_NEG,
          BloodType.O_POS
        };
    BedPackage[] packages1 =
        new BedPackage[] {
          new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 0),
          new BedPackage(PackageName.BLOOD_THIRSTY, 2),
          new BedPackage(PackageName.SWEET_TOOTH, 1)
        };
    beds.add(
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            BedType.LATEX,
            CleaningFrequency.ANNUAL,
            bloodTypes1,
            100,
            packages1));

    BloodType[] bloodTypes2 = new BloodType[] {BloodType.O_NEG};
    BedPackage[] packages2 = new BedPackage[] {new BedPackage(PackageName.SWEET_TOOTH, 0)};
    beds.add(
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            BedType.SPRINGS,
            CleaningFrequency.MONTHLY,
            bloodTypes2,
            200,
            packages2));

    BloodType[] bloodTypes3 = new BloodType[] {BloodType.O_POS, BloodType.A_NEG, BloodType.B_POS};
    BedPackage[] packages3 =
        new BedPackage[] {
          new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 0),
          new BedPackage(PackageName.BLOOD_THIRSTY, 2)
        };
    beds.add(
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            BedType.MEMORY_FOAM,
            CleaningFrequency.NEVER,
            bloodTypes3,
            1000,
            packages3));

    BloodType[] bloodTypes4 = new BloodType[] {BloodType.B_NEG, BloodType.B_POS, BloodType.O_POS};
    BedPackage[] packages4 = new BedPackage[] {new BedPackage(PackageName.BLOOD_THIRSTY, 2)};
    beds.add(
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            BedType.LATEX,
            CleaningFrequency.WEEKLY,
            bloodTypes4,
            350,
            packages4));

    BloodType[] bloodTypes5 = new BloodType[] {BloodType.A_POS, BloodType.O_NEG};
    BedPackage[] packages5 = new BedPackage[] {new BedPackage(PackageName.SWEET_TOOTH, 1)};
    beds.add(
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            BedType.SPRINGS,
            CleaningFrequency.NEVER,
            bloodTypes5,
            500,
            packages5));

    for (Bed bed : beds) {
      String uuid = UUID.randomUUID().toString();
      bed.setUuid(uuid);
      this.bedService.addBed(bed, uuid);
    }
  }

  private Booking createBooking() {
    Booking booking = new Booking();
    booking.setNumberOfNights(3);
    booking.setArrivalDate("2021-05-21");
    booking.setTenantPublicKey("72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC");
    booking.setBedPackage("allYouCanDrink");
    return booking;
  }

  @Test
  void sortBeds_withMultipleBeds_shouldBeSortedInDescendingStarsOrder() {
    createBeds();
    Query query = new Query("empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty");

    ArrayList<Bed> sortedBeds = this.bedService.Get(query);

    for (int i = 1; i < sortedBeds.size(); i++) {
      int previousBedStars = BedStarCalculator.calculateStars(sortedBeds.get(i - 1));
      int currentBedStars = BedStarCalculator.calculateStars(sortedBeds.get(i));
      assertTrue(previousBedStars >= currentBedStars);
    }
    assertEquals(sortedBeds.size(), 5);
  }

  @Test
  void filterBeds_withNoBedWasAdded_shouldEqualEmptyArrayList() {
    Query query = new Query("empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty");
    assertEquals(this.bedService.Get(query), new ArrayList<Bed>());
  }

  @Test
  void filterBeds_withParametersForEach_shouldFilterCorrectly() {
    createBeds();

    Query query =
        new Query(
            "allYouCanDrink",
            "memoryFoam",
            "never",
            "O+,A-,B+",
            "350",
            "private",
            "empty",
            "empty");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    assertEquals(filteredBeds.size(), 1);
    assertEquals(filteredBeds.get(0).getPackages()[0].getName(), PackageName.ALL_YOU_CAN_DRINK);
    assertEquals(filteredBeds.get(0).getBedType(), BedType.MEMORY_FOAM);
    assertEquals(filteredBeds.get(0).getCleaningFrequency(), CleaningFrequency.NEVER);
    assertTrue(Arrays.asList(filteredBeds.get(0).getBloodTypes()).contains(BloodType.O_POS));
    assertTrue(Arrays.asList(filteredBeds.get(0).getBloodTypes()).contains(BloodType.A_NEG));
    assertTrue(Arrays.asList(filteredBeds.get(0).getBloodTypes()).contains(BloodType.B_POS));
    assertTrue(filteredBeds.get(0).getCapacity() >= 350);
    assertEquals(filteredBeds.get(0).getLodgingMode(), LodgingMode.PRIVATE);
  }

  @Test
  void filterBeds_withParameters1_shouldFilterCorrectly() {
    createBeds();

    Query query =
        new Query("empty", "memoryFoam", "empty", "empty", "empty", "empty", "empty", "empty");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    for (Bed bed : filteredBeds) {
      assertEquals(bed.getBedType(), BedType.MEMORY_FOAM);
    }
    assertEquals(filteredBeds.size(), 1);
  }

  @Test
  void filterBeds_withParameters2_shouldFilterCorrectly() {
    createBeds();

    Query query =
        new Query("sweetTooth", "springs", "empty", "O-", "empty", "empty", "empty", "empty");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    for (Bed bed : filteredBeds) {
      assertEquals(bed.getBedType(), BedType.SPRINGS);
      assertTrue(Arrays.asList(bed.packagesNames()).contains(PackageName.SWEET_TOOTH));
      assertTrue(Arrays.asList(bed.getBloodTypes()).contains(BloodType.O_NEG));
    }
    assertEquals(filteredBeds.size(), 2);
  }

  @Test
  void filterBeds_withParameters3_shouldFilterCorrectly() {
    createBeds();

    Query query = new Query("empty", "empty", "never", "empty", "400", "empty", "empty", "empty");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    for (Bed bed : filteredBeds) {
      assertEquals(bed.getCleaningFrequency(), CleaningFrequency.NEVER);
      assertTrue(bed.getCapacity() >= 400);
    }
    assertEquals(filteredBeds.size(), 2);
  }

  @Test
  void filterBeds_withNoParameters_shouldReturnAllBeds() {
    Query query = new Query("empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty");
    assertEquals(this.bedService.Get(query), this.bedService.getAllBeds());
  }

  @Test
  void filterBeds_withTwoEmptyBedsOneFullWholeWeek_shouldReturnTwoEmptyBeds() {
    Bed bed1 = createBed(BedType.SPRINGS, CleaningFrequency.NEVER, PackageName.BLOOD_THIRSTY, 100);
    Bed bed2 =
        createBed(BedType.MEMORY_FOAM, CleaningFrequency.ANNUAL, PackageName.SWEET_TOOTH, 100);
    Bed bed3 =
        createBed(BedType.LATEX, CleaningFrequency.WEEKLY, PackageName.ALL_YOU_CAN_DRINK, 100);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String bookingArrivalDate = LocalDate.now().format(formatter);
    int bookingNumberOfNights = 3;
    int bookingColonySize = 80;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed3.addBooking(booking);

    String queryArrivalDate = "empty";
    String queryNumberOfNights = "empty";
    String queryMinCapacity = "50";
    Query query =
        new Query(
            "empty",
            "empty",
            "empty",
            "empty",
            queryMinCapacity,
            "empty",
            queryArrivalDate,
            queryNumberOfNights);

    List<Bed> expected = Arrays.asList(bed1, bed2);
    Collections.sort(expected, new BedStarComparator());
    ArrayList<Bed> expected_beds = new ArrayList<>(expected);
    Collections.reverse(expected_beds);
    assertEquals(expected_beds, this.bedService.Get(query));
  }

  @Test
  void filterBeds_withTwoEmptyBedsOneConflictingForADay_shouldReturnTwoEmptyBeds() {
    Bed bed1 = createBed(BedType.SPRINGS, CleaningFrequency.NEVER, PackageName.BLOOD_THIRSTY, 100);
    Bed bed2 =
        createBed(BedType.MEMORY_FOAM, CleaningFrequency.ANNUAL, PackageName.SWEET_TOOTH, 100);
    Bed bed3 =
        createBed(BedType.LATEX, CleaningFrequency.WEEKLY, PackageName.ALL_YOU_CAN_DRINK, 100);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String bookingArrivalDate = LocalDate.parse("2020-05-20").format(formatter);
    int bookingNumberOfNights = 3;
    int bookingColonySize = 80;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed3.addBooking(booking);

    String queryArrivalDate = "2020-05-19";
    String queryNumberOfNights = "empty";
    String queryMinCapacity = "50";
    Query query =
        new Query(
            "empty",
            "empty",
            "empty",
            "empty",
            queryMinCapacity,
            "empty",
            queryArrivalDate,
            queryNumberOfNights);

    List<Bed> beds = Arrays.asList(bed1, bed2);
    beds.sort(new BedStarComparator());
    ArrayList<Bed> expected_beds = new ArrayList<>(beds);
    Collections.reverse(expected_beds);
    assertEquals(expected_beds, this.bedService.Get(query));
  }

  @Test
  void filterBeds_withTwoEmptyBedsOneBookedButStillRoom_shouldReturnAllBeds() {
    Bed bed1 = createBed(BedType.SPRINGS, CleaningFrequency.NEVER, PackageName.BLOOD_THIRSTY, 100);
    Bed bed2 =
        createBed(BedType.MEMORY_FOAM, CleaningFrequency.ANNUAL, PackageName.SWEET_TOOTH, 100);
    Bed bed3 =
        createBed(BedType.LATEX, CleaningFrequency.WEEKLY, PackageName.ALL_YOU_CAN_DRINK, 100);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String bookingArrivalDate = LocalDate.now().format(formatter);
    int bookingNumberOfNights = 3;
    int bookingColonySize = 40;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed3.addBooking(booking);

    String queryArrivalDate = "empty";
    String queryNumberOfNights = "empty";
    String queryMinCapacity = "50";
    Query query =
        new Query(
            "empty",
            "empty",
            "empty",
            "empty",
            queryMinCapacity,
            "empty",
            queryArrivalDate,
            queryNumberOfNights);

    List<Bed> beds = Arrays.asList(bed1, bed2, bed3);
    beds.sort(new BedStarComparator());
    ArrayList<Bed> expected_beds = new ArrayList<>(beds);
    Collections.reverse(expected_beds);
    assertEquals(expected_beds, this.bedService.Get(query));
  }

  @Test
  void filterBeds_withThreeFullBeds_shouldReturnEmptyList() {
    Bed bed1 = createBed(BedType.SPRINGS, CleaningFrequency.NEVER, PackageName.BLOOD_THIRSTY, 100);
    Bed bed2 =
        createBed(BedType.MEMORY_FOAM, CleaningFrequency.ANNUAL, PackageName.SWEET_TOOTH, 100);
    Bed bed3 =
        createBed(BedType.LATEX, CleaningFrequency.WEEKLY, PackageName.ALL_YOU_CAN_DRINK, 100);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String bookingArrivalDate = LocalDate.now().format(formatter);
    int bookingNumberOfNights = 3;
    int bookingColonySize = 100;
    Booking booking = createBooking(bookingArrivalDate, bookingNumberOfNights, bookingColonySize);
    bed1.addBooking(booking);
    bed2.addBooking(booking);
    bed3.addBooking(booking);

    String queryArrivalDate = "empty";
    String queryNumberOfNights = "empty";
    String queryMinCapacity = "50";
    Query query =
        new Query(
            "empty",
            "empty",
            "empty",
            "empty",
            queryMinCapacity,
            "empty",
            queryArrivalDate,
            queryNumberOfNights);

    ArrayList<Bed> expected_beds = new ArrayList<>();
    Collections.reverse(expected_beds);
    assertEquals(expected_beds, this.bedService.Get(query));
  }

  private Bed createBed(
      BedType bedType, CleaningFrequency cleaningFrequency, PackageName packageName, int capacity) {
    BloodType[] bloodTypes1 =
        new BloodType[] {
          BloodType.A_NEG,
          BloodType.A_POS,
          BloodType.AB_NEG,
          BloodType.AB_POS,
          BloodType.B_NEG,
          BloodType.B_POS,
          BloodType.O_NEG,
          BloodType.O_POS
        };
    BedPackage[] packages1 =
        new BedPackage[] {
          new BedPackage(packageName, 0),
        };
    Bed bed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            bedType,
            cleaningFrequency,
            bloodTypes1,
            capacity,
            packages1);
    String uuid = UUID.randomUUID().toString();
    bed.setUuid(uuid);
    bedService.addBed(bed, uuid);
    return bed;
  }

  private Booking createBooking(String arrivalDate, int numberOfNights, int colonySize) {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String bedPackage = "allYouCanDrink";
    Booking booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);
    booking.setColonySize(colonySize);
    return booking;
  }

  @Test
  void getTotalNumberOfBeds_withNoBedWasAdded_shouldEquals0() {
    assertEquals(0, this.bedService.getTotalNumberOfBeds());
  }

  @Test
  void addBed_withCreatingOneValidBed_shouldAddOneBedToHashMap() {
    this.bedService.addBed(this.bed, this.bedUuid);
    assertEquals(1, bedService.getTotalNumberOfBeds());
  }

  @Test
  void addBed_withCreatingOneValidBed_shouldBeEqualToFirstIndexOfGetAllBeds() {
    this.bedService.addBed(this.bed, this.bedUuid);
    assertEquals(bed, this.bedService.getAllBeds().get(0));
  }

  @Test
  void getBedByUuid_withGettingBedWithValidUuid_shouldEqualSameBed() {
    this.bedService.addBed(this.bed, this.bedUuid);
    assertEquals(bed, this.bedService.getBedByUuid(this.bedUuid));
  }

  @Test
  void getBedByUuid_withGettingBedWithInvalidUuid_shouldThrow() {
    this.bedService.addBed(this.bed, this.bedUuid);
    String invalidUuid = "";
    assertThrows(BedNotFoundException.class, () -> this.bedService.getBedByUuid(invalidUuid));
  }

  @Test
  void removeBed_withGivingValidUUID_shouldWork() {
    assertEquals(0, bedService.getTotalNumberOfBeds());
    this.bedService.addBed(this.bed, this.bedUuid);
    assertEquals(1, bedService.getTotalNumberOfBeds());
    bedService.removeBed(this.bedUuid);
    assertEquals(0, bedService.getTotalNumberOfBeds());
  }

  @Test
  void cancelBooking_withBookingExist_shouldSetTheBookingStatusAsCanceled() {
    Booking booking = createBooking();
    String bookingUuid = this.bed.addBooking(booking);
    this.bed.cancelBooking(bookingUuid);
    assertEquals(
        BookingStatus.CANCELED.getLabel(),
        this.bed.getBookingByUuid(bookingUuid).getBookingStatus());
  }
}
