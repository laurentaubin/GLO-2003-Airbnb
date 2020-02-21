package bed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BedTest {

  @Test
  void creatingBed_withValidParameters_shouldNotThrow() throws Exception {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    Bed.BedType bedType = Bed.BedType.LATEX;
    Bed.CleaningFrequency cleaningFrequency = Bed.CleaningFrequency.MONTHLY;
    Bed.BloodType[] bloodTypes = new Bed.BloodType[] {Bed.BloodType.O_NEG, Bed.BloodType.AB_POS};
    int capacity = 950;
    Bed.BedPackage[] packages =
        new Bed.BedPackage[] {
          new Bed.BedPackage(Bed.BedPackage.Name.BLOOD_THIRSTY, 12.5),
          new Bed.BedPackage(Bed.BedPackage.Name.SWEET_TOOTH, 6)
        };

    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
  }

  @Test
  void gettingBloodTypeValueOfLabel_withValidLabel_shouldReturnBloodType() throws Exception {
    Bed.BloodType bloodType = Bed.BloodType.valueOfLabel("A+");

    assertEquals(Bed.BloodType.A_POS, bloodType);
  }

  @Test
  void gettingBloodTypeValueOfLabel_withInvalidLabel_shouldThrowIllegalArgumentException()
      throws Exception {

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> Bed.BloodType.valueOfLabel("C+"));

    String expectedMessage = "Invalid blood type";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void gettingCleaningFrequencyValueOfLabel_withValidLabel_shouldReturnCleaningFrequency() {
    Bed.CleaningFrequency cleaningFrequency = Bed.CleaningFrequency.valueOfLabel("annual");

    assertEquals(Bed.CleaningFrequency.ANNUAL, cleaningFrequency);
  }

  @Test
  void gettingCleaningFrequencyValueOfLabel_withInvalidLabel_shouldThrowIllegalArgumentException() {
    Exception exception =
        assertThrows(
            IllegalArgumentException.class, () -> Bed.CleaningFrequency.valueOfLabel("daily"));

    String expectedMessage = "Invalid cleaning frequency";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void gettingBedTypeValueOfLabel_withValidLabel_shouldReturnBedType() {
    Bed.BedType bedType = Bed.BedType.valueOfLabel("latex");

    assertEquals(Bed.BedType.LATEX, bedType);
  }

  @Test
  void gettingBedTypeValueOdLabel_withInvalidLabel_shouldThrowIllegalArgumentException() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> Bed.BedType.valueOfLabel("strong"));

    String expectedMessage = "Invalid bed type";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }
}
