package infrastructure.bed;

import static org.junit.jupiter.api.Assertions.*;

import domain.bed.*;
import domain.bed.enums.*;
import domain.booking.exception.BedNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BedRepositoryTest {
  private String ownerPublicKey =
      "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  private String zipCode = "12345";
  private BedType bedType = BedType.LATEX;
  private CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
  private BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
  private int capacity = 950;
  private BedPackage[] packages =
      new BedPackage[] {
        new BedPackage(PackageName.BLOOD_THIRSTY, 12.5), new BedPackage(PackageName.SWEET_TOOTH, 6)
      };
  private Bed bed;
  private String bedUuid = UUID.randomUUID().toString();
  private BedRepository bedRepository;

  @BeforeEach
  void setUp() {
    this.bedRepository = new BedRepository();
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

  @Test
  void addBed_withCreatingOneValidBed_shouldAddOneBedToRepo() {
    this.bedRepository.addBed(this.bedUuid, this.bed);
    assertEquals(1, bedRepository.getTotalNumberOfBeds());
  }

  @Test
  void addBed_withCreatingOneValidBed_shouldBeEqualToFirstIndexOfGetAllBeds() {
    this.bedRepository.addBed(this.bedUuid, this.bed);
    assertEquals(bed, this.bedRepository.getAllBeds().get(0));
  }

  @Test
  void getTotalNumberOfBeds_withNoBedWasAdded_shouldReturnEmptyBedRepo() {
    assertEquals(0, this.bedRepository.getTotalNumberOfBeds());
  }

  @Test
  void getBedByUuid_withGettingBedWithValidUuid_shouldEqualSameBed() {
    this.bedRepository.addBed(this.bedUuid, this.bed);
    assertEquals(bed, this.bedRepository.getBed(this.bedUuid));
  }

  @Test
  void getBedByUuid_withGettingBedWithInvalidUuid_shouldThrow() {
    this.bedRepository.addBed(this.bedUuid, this.bed);
    String invalidUuid = "";
    assertThrows(BedNotFoundException.class, () -> this.bedRepository.getBed(invalidUuid));
  }

  @Test
  void removeBed_withGivingValidUUID_shouldWork() {
    assertEquals(0, bedRepository.getTotalNumberOfBeds());
    this.bedRepository.addBed(this.bedUuid, this.bed);
    assertEquals(1, bedRepository.getTotalNumberOfBeds());
    bedRepository.removeBed(this.bedUuid);
    assertEquals(0, bedRepository.getTotalNumberOfBeds());
  }

  @Test
  void clearAllBeds_withCreatingOneValidBed_shouldWork() {
    assertEquals(0, this.bedRepository.getTotalNumberOfBeds());
    this.bedRepository.addBed(this.bedUuid, this.bed);
    assertEquals(1, this.bedRepository.getTotalNumberOfBeds());
    this.bedRepository.clearAllBeds();
    assertEquals(0, this.bedRepository.getTotalNumberOfBeds());
  }
}
