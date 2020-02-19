package Bed;

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
}
