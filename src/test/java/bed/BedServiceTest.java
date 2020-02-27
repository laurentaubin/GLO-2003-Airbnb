package bed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedServiceTest {
  private bed.BedService bedService;
  String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  String zipCode = "12345";
  BedType bedType = BedType.LATEX;
  CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
  BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
  int capacity = 950;
  BedPackage[] packages =
      new BedPackage[] {
        new BedPackage(BedPackage.Name.BLOOD_THIRSTY, 12.5),
        new BedPackage(BedPackage.Name.SWEET_TOOTH, 6)
      };

  @BeforeEach
  void setUp() {
    this.bedService = new bed.BedService();
  }

  @Test
  void getTotalNumberOfBeds_whenNoBedWasAdded_shouldEquals0() {
    assertEquals(0, this.bedService.getTotalNumberOfBeds());
  }

  @Test
  void addBed_whenCreatingOneValidBed_shouldAddOneBedToHashMap() {
    this.bedService.addBed(
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages));
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
    this.bedService.addBed(bed);
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
    String uuid = this.bedService.addBed(bed);
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
    this.bedService.addBed(bed);
    String invalidUuid = "";
    assertThrows(IllegalArgumentException.class, () -> this.bedService.getBedByUuid(invalidUuid));
  }
}
