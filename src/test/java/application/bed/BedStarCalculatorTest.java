package application.bed;

import static org.junit.jupiter.api.Assertions.*;

import domain.bed.Bed;
import domain.bed.enums.*;
import org.junit.jupiter.api.Test;

class BedStarCalculatorTest {

  @Test
  void calculatingStars_withBedHavingScoreOf200_shouldReturn3() {
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

    assertEquals(3, BedStarCalculator.calculateStars(bedTest));
  }

  @Test
  void calculatingStars_withBedHavingScoreOf2250_shouldReturn5() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.SPRINGS;
    CleaningFrequency cleaningFrequency = CleaningFrequency.NEVER;
    BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG};
    int capacity = 950;
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(PackageName.BLOOD_THIRSTY, 12.5),
          new BedPackage(PackageName.SWEET_TOOTH, 6)
        };
    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);

    assertEquals(5, BedStarCalculator.calculateStars(bedTest));
  }

  @Test
  void calculatingStars_withBedHavingScoreOf12_shouldReturn1() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.LATEX;
    CleaningFrequency cleaningFrequency = CleaningFrequency.WEEKLY;
    BloodType[] bloodTypes = new BloodType[] {BloodType.AB_POS};
    int capacity = 950;
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(PackageName.BLOOD_THIRSTY, 12.5),
          new BedPackage(PackageName.SWEET_TOOTH, 6)
        };
    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);

    assertEquals(1, BedStarCalculator.calculateStars(bedTest));
  }
}
