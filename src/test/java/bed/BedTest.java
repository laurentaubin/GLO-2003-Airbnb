package bed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BedTest {

  @Test
  void creatingBed_withValidParameters_shouldNotThrow() throws Exception {
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

    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
  }

  @Test
  void gettingStarRating_withValidParameters_shouldNotThrow() throws Exception {
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
    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);

    if (bedTest.getNumberOfStars() != 3) {
      throw new Exception("Le résultat calculé est incorrect");
    }
  }

  @Test
  void gettingMaxStarRating_withValidParameters_shouldNotThrow() throws Exception {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.SPRINGS;
    CleaningFrequency cleaningFrequency = CleaningFrequency.NEVER;
    BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG};
    int capacity = 950;
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(BedPackage.Name.BLOOD_THIRSTY, 12.5),
          new BedPackage(BedPackage.Name.SWEET_TOOTH, 6)
        };
    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);

    assertEquals(bedTest.getNumberOfStars(), 5);
  }

  @Test
  void gettingMinStarRating_withValidParameters_shouldNotThrow() throws Exception {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.LATEX;
    CleaningFrequency cleaningFrequency = CleaningFrequency.WEEKLY;
    BloodType[] bloodTypes = new BloodType[] {BloodType.AB_POS};
    int capacity = 950;
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(BedPackage.Name.BLOOD_THIRSTY, 12.5),
          new BedPackage(BedPackage.Name.SWEET_TOOTH, 6)
        };
    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);

    assertEquals(bedTest.getNumberOfStars(), 1);
  }

  @Test
  void gettingStarRating_withEmptyBed_shouldNotThrow() throws Exception {
    Bed bedTest = new Bed();

    assertEquals(bedTest.getNumberOfStars(), -1);
  }
}
