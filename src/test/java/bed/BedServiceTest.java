package bed;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.booking.BedNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
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

  @BeforeEach
  void setUp() {
    this.bedService.clearAllBeds();
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

  @Test
  void sortBeds_whenMultipleBeds_shouldBeSortedInDescendingStarsOrder() {
    createBeds();
    Query query = new Query("empty", "empty", "empty", "empty", "0");

    ArrayList<Bed> sortedBeds = this.bedService.Get(query);

    for (int i = 1; i < sortedBeds.size(); i++) {
      assertTrue(sortedBeds.get(i - 1).getStars() >= sortedBeds.get(i).getStars());
    }
    assertEquals(sortedBeds.size(), 5);
  }

  @Test
  void filterBeds_whenNoBedWasAdded_shouldEqualEmptyArrayList() {
    Query query = new Query("empty", "empty", "empty", "empty", "0");
    assertEquals(this.bedService.Get(query), new ArrayList<Bed>());
  }

  @Test
  void filterBeds_parametersForEach_shouldFilterCorrectly() {
    createBeds();

    Query query = new Query("allYouCanDrink", "memoryFoam", "never", "O+,A-,B+", "350");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    assertEquals(filteredBeds.size(), 1);
    assertEquals(filteredBeds.get(0).getPackages()[0].getName(), PackageName.ALL_YOU_CAN_DRINK);
    assertEquals(filteredBeds.get(0).getBedType(), BedType.MEMORY_FOAM);
    assertEquals(filteredBeds.get(0).getCleaningFrequency(), CleaningFrequency.NEVER);
    assertTrue(Arrays.asList(filteredBeds.get(0).getBloodTypes()).contains(BloodType.O_POS));
    assertTrue(Arrays.asList(filteredBeds.get(0).getBloodTypes()).contains(BloodType.A_NEG));
    assertTrue(Arrays.asList(filteredBeds.get(0).getBloodTypes()).contains(BloodType.B_POS));
    assertTrue(filteredBeds.get(0).getCapacity() >= 350);
  }

  @Test
  void filterBeds_parameters1_shouldFilterCorrectly() {
    createBeds();

    Query query = new Query("empty", "memoryFoam", "empty", "empty", "0");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    for (Bed bed : filteredBeds) {
      assertEquals(bed.getBedType(), BedType.MEMORY_FOAM);
    }
    assertEquals(filteredBeds.size(), 1);
  }

  @Test
  void filterBeds_parameters2_shouldFilterCorrectly() {
    createBeds();

    Query query = new Query("sweetTooth", "springs", "empty", "O-", "0");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    for (Bed bed : filteredBeds) {
      assertEquals(bed.getBedType(), BedType.SPRINGS);
      assertTrue(Arrays.asList(bed.packagesNames()).contains(PackageName.SWEET_TOOTH));
      assertTrue(Arrays.asList(bed.getBloodTypes()).contains(BloodType.O_NEG));
    }
    assertEquals(filteredBeds.size(), 2);
  }

  @Test
  void filterBeds_parameters3_shouldFilterCorrectly() {
    createBeds();

    Query query = new Query("empty", "empty", "never", "empty", "400");
    ArrayList<Bed> filteredBeds = this.bedService.Get(query);

    for (Bed bed : filteredBeds) {
      assertEquals(bed.getCleaningFrequency(), CleaningFrequency.NEVER);
      assertTrue(bed.getCapacity() >= 400);
    }
    assertEquals(filteredBeds.size(), 2);
  }

  @Test
  void filterBeds_noParameters_shouldReturnAllBeds() {
    Query query = new Query("empty", "empty", "empty", "empty", "0");
    assertEquals(this.bedService.Get(query), this.bedService.getAllBeds());
  }

  @Test
  void getTotalNumberOfBeds_whenNoBedWasAdded_shouldEquals0() {
    assertEquals(0, this.bedService.getTotalNumberOfBeds());
  }

  @Test
  void addBed_whenCreatingOneValidBed_shouldAddOneBedToHashMap() {
    Bed bed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages);
    String uuid = UUID.randomUUID().toString();
    bed.setUuid(uuid);
    this.bedService.addBed(bed, uuid);
    assertEquals(1, bedService.getTotalNumberOfBeds());
  }

  @Test
  void addBed_whenCreatingOneValidBed_shouldBeEqualToFirstIndexOfGetAllBeds() {
    Bed bed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages);
    String uuid = UUID.randomUUID().toString();
    bed.setUuid(uuid);
    this.bedService.addBed(bed, uuid);
    assertEquals(bed, this.bedService.getAllBeds().get(0));
  }

  @Test
  void getBedByUuid_whenGettingBedWithValidUuid_shouldEqualSameBed() {
    Bed bed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages);
    String uuid = UUID.randomUUID().toString();
    bed.setUuid(uuid);
    this.bedService.addBed(bed, uuid);
    assertEquals(bed, this.bedService.getBedByUuid(uuid));
  }

  @Test
  void getBedByUuid_whenGettingBedWithInvalidUuid_shouldThrow() {
    Bed bed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages);
    String uuid = UUID.randomUUID().toString();
    bed.setUuid(uuid);
    this.bedService.addBed(bed, uuid);
    String invalidUuid = "";
    assertThrows(BedNotFoundException.class, () -> this.bedService.getBedByUuid(invalidUuid));
  }

  @Test
  void removeBed_whenGivingValidUUID_shouldWork() {
    assertEquals(0, bedService.getTotalNumberOfBeds());
    Bed bed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages);
    String uuid = UUID.randomUUID().toString();
    bed.setUuid(uuid);
    this.bedService.addBed(bed, uuid);
    assertEquals(1, bedService.getTotalNumberOfBeds());
    bedService.removeBed(uuid);
    assertEquals(0, bedService.getTotalNumberOfBeds());
  }
}
