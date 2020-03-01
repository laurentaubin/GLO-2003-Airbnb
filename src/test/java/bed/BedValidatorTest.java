package bed;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.bed.InvalidAllYouCanDrinkException;
import exceptions.bed.InvalidSweetToothPackageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BedValidatorTest {
  private BedValidator bedValidator;

  @BeforeEach
  void setup() {
    this.bedValidator = new BedValidator();
  }

  @Test
  void isPublicKeyValid_whenValidKey_shouldBeTrue() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertTrue(this.bedValidator.isPublicKeyValid(ownerPublicKey));
  }

  @Test
  void isPublicKeyValid_whenKeyShorterThan64Char_shouldBeFalse() {
    String ownerPublicKey = "8F0436A6F049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertFalse(this.bedValidator.isPublicKeyValid(ownerPublicKey));
    ownerPublicKey = "8F0436A6F049085B7F19AB73933973BF21276276F2EC7D122AB46A3A4E";
    assertFalse(this.bedValidator.isPublicKeyValid(ownerPublicKey));
    ownerPublicKey = "8F0436A6F049085B7F19AB7393397F2EC7D122AB46A3A4E";
    assertFalse(this.bedValidator.isPublicKeyValid(ownerPublicKey));
  }

  @Test
  void isPublicKeyValid_whenKeyLongerThan64Char_shouldBeFalse() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BA3B46A3A4E";
    assertFalse(this.bedValidator.isPublicKeyValid(ownerPublicKey));
    ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7LSKJFDLKAJD122AC110BA3B46A3A4E";
    assertFalse(this.bedValidator.isPublicKeyValid(ownerPublicKey));
  }

  @Test
  void isPublicKeyValid_whenKeyContainsSpecialCharacters_shouldBeFalse() {
    String ownerPublicKey = "8F0436A6FB049&85B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertFalse(this.bedValidator.isPublicKeyValid(ownerPublicKey));
  }

  @Test
  void isZipCodeValid_whenValidZipCode_shouldBeTrue() {
    String zipCode = "12345";
    assertTrue(this.bedValidator.isZipCodeValid(zipCode));
    zipCode = "76321";
    assertTrue(this.bedValidator.isZipCodeValid(zipCode));
    zipCode = "23429";
    assertTrue(this.bedValidator.isZipCodeValid(zipCode));
  }

  @Test
  void isZipCodeValid_whenZipCodeContainsLetters_shouldBeFalse() {
    String zipCode = "12A45";
    assertFalse(this.bedValidator.isZipCodeValid(zipCode));
    zipCode = "1C1B5";
    assertFalse(this.bedValidator.isZipCodeValid(zipCode));
  }

  @Test
  void isZipCodeValid_whenZipCodeLessThanFiveNumbers_shouldBeFalse() {
    String zipCode = "1234";
    assertFalse(this.bedValidator.isZipCodeValid(zipCode));
  }

  @Test
  void isZipCodeValid_whenZipCodeMoreThanFiveNumbers_shouldBeFalse() {
    String zipCode = "123456";
    assertFalse(this.bedValidator.isZipCodeValid(zipCode));
  }

  @Test
  void isCapacityValid_whenCapacityIsPositive_shouldBeTrue() {
    int capacity = 1;
    assertTrue(this.bedValidator.isCapacityValid(capacity));
    capacity = 902;
    assertTrue(this.bedValidator.isCapacityValid(capacity));
  }

  @Test
  void isCapacityValid_whenCapacityIsNegative_shouldBeFalse() {
    int capacity = -1;
    assertFalse(this.bedValidator.isCapacityValid(capacity));
  }

  @Test
  void isBedPackageValid_whenOfferingSweetToothWithAllOtherPackage_shouldNotThrow() {
    BedPackage[] bedPackages = new BedPackage[3];
    bedPackages[0] = new BedPackage(PackageName.SWEET_TOOTH, 0);
    bedPackages[1] = new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 0);
    bedPackages[2] = new BedPackage(PackageName.BLOOD_THIRSTY, 0);
    assertDoesNotThrow(
        () -> {
          this.bedValidator.validateBedPackage(bedPackages);
        });
  }

  @Test
  void isBedPackageValid_whenOfferingSweetToothWithoutOtherPackages_shouldThrow() {
    BedPackage[] bedPackages = new BedPackage[1];
    bedPackages[0] = new BedPackage(PackageName.SWEET_TOOTH, 0);
    assertThrows(
        InvalidSweetToothPackageException.class,
        () -> {
          this.bedValidator.validateBedPackage(bedPackages);
        });
  }

  @Test
  void isBedPackageValid_whenOfferingSweetToothWithoutBloodthirsty_shouldThrow() {
    BedPackage[] bedPackages = new BedPackage[2];
    bedPackages[0] = new BedPackage(PackageName.SWEET_TOOTH, 0);
    bedPackages[1] = new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 0);
    assertThrows(
        InvalidSweetToothPackageException.class,
        () -> {
          this.bedValidator.validateBedPackage(bedPackages);
        });
  }

  @Test
  void isBedPackageValid_whenOfferingSweetToothWithoutAllYouCanDrink_shouldThrow() {
    BedPackage[] bedPackages = new BedPackage[2];
    bedPackages[0] = new BedPackage(PackageName.SWEET_TOOTH, 0);
    bedPackages[1] = new BedPackage(PackageName.BLOOD_THIRSTY, 0);
    assertThrows(
        InvalidSweetToothPackageException.class,
        () -> {
          this.bedValidator.validateBedPackage(bedPackages);
        });
  }

  @Test
  void isBedPackageValid_whenOfferingAllYouCanDrinkWithBloodThirsty_shouldNotThrow() {
    BedPackage[] bedPackages = new BedPackage[2];
    bedPackages[0] = new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 0);
    bedPackages[1] = new BedPackage(PackageName.BLOOD_THIRSTY, 0);
    assertDoesNotThrow(
        () -> {
          this.bedValidator.validateBedPackage(bedPackages);
        });
  }

  @Test
  void isBedPackageValid_whenOfferingAllYouCanDrinkWithoutBloothirsty_shouldThrow() {
    BedPackage[] bedPackages = new BedPackage[1];
    bedPackages[0] = new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 0);
    assertThrows(
        InvalidAllYouCanDrinkException.class,
        () -> {
          this.bedValidator.validateBedPackage(bedPackages);
        });
  }

  @Test
  void isBedPackageValid_whenOfferingBloodThirstyAlone_shouldNotThrow() {
    BedPackage[] bedPackages = new BedPackage[1];
    bedPackages[0] = new BedPackage(PackageName.BLOOD_THIRSTY, 0);
  }
}
