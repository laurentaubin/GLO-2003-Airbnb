public class Bed {
  public enum BedType {
    LATEX,
    MEMORYFOAM,
    SPRINGS
  }

  public enum CleaningFrequency {
    WEEKLY,
    MONTHLY,
    ANNUAL,
    NEVER
  }

  public enum BloodType {
    ONEG,
    OPOS,
    ANEG,
    APOS,
    BNEG,
    BPOS,
    ABNEG,
    ABPOS
  }

  public static class BedPackage {
    private Name name;
    private double pricePerNight;

    public enum Name {
      BLOODTHIRSTY,
      ALLYOUCANDRINK,
      SWEETTOOTH
    }

    public BedPackage(Name name, double pricePerNight) {}
  }

  public Bed(
      String ownerPublicKey,
      String zipCode,
      BedType bedType,
      CleaningFrequency cleaningFrequency,
      BloodType[] bloodTypes,
      int capacity,
      BedPackage[] packages) {}
}
