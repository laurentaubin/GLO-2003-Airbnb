package bed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BedTest {

  @Test
  void creatingBed_withValidParameters_shouldNotThrow() throws Exception {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    bed.BedType bedType = bed.BedType.LATEX;
    bed.CleaningFrequency cleaningFrequency = bed.CleaningFrequency.MONTHLY;
    bed.BloodType[] bloodTypes = new bed.BloodType[] {bed.BloodType.O_NEG, bed.BloodType.AB_POS};
    int capacity = 950;
    bed.BedPackage[] packages =
        new bed.BedPackage[] {
          new bed.BedPackage(bed.BedPackage.Name.BLOOD_THIRSTY, 12.5),
          new bed.BedPackage(bed.BedPackage.Name.SWEET_TOOTH, 6)
        };

    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
  }

  @Test
  void gettingBloodTypeValueOfLabel_withValidLabel_shouldReturnBloodType() throws Exception {
    bed.BloodType bloodType = bed.BloodType.valueOfLabel("A+");

    assertEquals(bed.BloodType.A_POS, bloodType);
  }

  @Test
  void gettingBloodTypeValueOfLabel_withInvalidLabel_shouldThrowIllegalArgumentException()
      throws Exception {

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> bed.BloodType.valueOfLabel("C+"));

    String expectedMessage = "Invalid blood type";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void gettingCleaningFrequencyValueOfLabel_withValidLabel_shouldReturnCleaningFrequency() {
    bed.CleaningFrequency cleaningFrequency = bed.CleaningFrequency.valueOfLabel("annual");

    assertEquals(bed.CleaningFrequency.ANNUAL, cleaningFrequency);
  }

  @Test
  void gettingCleaningFrequencyValueOfLabel_withInvalidLabel_shouldThrowIllegalArgumentException() {
    Exception exception =
        assertThrows(
            IllegalArgumentException.class, () -> bed.CleaningFrequency.valueOfLabel("daily"));

    String expectedMessage = "Invalid cleaning frequency";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void gettingBedTypeValueOfLabel_withValidLabel_shouldReturnBedType() {
    bed.BedType bedType = bed.BedType.valueOfLabel("latex");

    assertEquals(bed.BedType.LATEX, bedType);
  }

  @Test
  void gettingBedTypeValueOdLabel_withInvalidLabel_shouldThrowIllegalArgumentException() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> bed.BedType.valueOfLabel("strong"));

    String expectedMessage = "Invalid bed type";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}
