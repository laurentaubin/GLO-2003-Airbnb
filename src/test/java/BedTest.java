import org.junit.jupiter.api.Test;

public class BedTest {
  private String ownerPublicKey =
      "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  private String zipCode = "12345";
  private Bed.BedType bedType = Bed.BedType.LATEX;
  private Bed.CleaningFrequency cleaningFrenquency = Bed.CleaningFrequency.MONTHLY;
  private Bed.BloodType[] bloodTypes =
      new Bed.BloodType[] {Bed.BloodType.ONEG, Bed.BloodType.ABPOS};
  private int capacity = 950;
  private Bed.BedPackage[] packages =
      new Bed.BedPackage[] {
        new Bed.BedPackage(Bed.BedPackage.Name.BLOODTHIRSTY, 12.5),
        new Bed.BedPackage(Bed.BedPackage.Name.SWEETTOOTH, 6)
      };

  @Test
  void creatingBed_withValidParameters_shouldWork() {
    Bed bedTest =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrenquency, bloodTypes, capacity, packages);
  }
}
