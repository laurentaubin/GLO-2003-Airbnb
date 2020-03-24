package domain.bed.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BedPackageTest {

  @Test
  void equals_usingUnequalBedPackages_shouldEqualFalse() {
    BedPackage bloodThirstyBedPackage = new BedPackage(PackageName.BLOOD_THIRSTY, 12.5);
    BedPackage sweetToothBedPackage = new BedPackage(PackageName.SWEET_TOOTH, 6);
    assertEquals(false, sweetToothBedPackage.equals(bloodThirstyBedPackage));
  }

  @Test
  void equals_usingUnequalPackageNames_shouldEqualFalse() {
    BedPackage bloodThirstyBedPackage = new BedPackage(PackageName.BLOOD_THIRSTY, 12.5);
    BedPackage sweetToothBedPackage = new BedPackage(PackageName.SWEET_TOOTH, 12.5);
    assertEquals(false, sweetToothBedPackage.equals(bloodThirstyBedPackage));
  }

  @Test
  void equals_usingEqualBedPackages_shouldEqualTrue() {
    BedPackage allYouCanDrinkBedPackage1 = new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 12.5);
    BedPackage allYouCanDrinkBedPackage2 = new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 12.5);
    assertEquals(true, allYouCanDrinkBedPackage1.equals(allYouCanDrinkBedPackage2));
  }

  @Test
  void equals_usingDummyObjects_shouldEqualFalse() {
    BedPackage allYouCanDrinkBedPackage = new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 12.5);
    Object dummyObject = new Object();
    assertEquals(false, allYouCanDrinkBedPackage.equals(dummyObject));
  }
}
