package bed;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.bed.PackageName.InvalidPackageNameException;
import org.junit.jupiter.api.Test;

class PackageNameTest {
  @Test
  void valueOfLabel_usingValidString_shouldEqualLabel() {
    String allYouCanDrink = "allYouCanDrink";
    assertEquals(PackageName.ALL_YOU_CAN_DRINK, PackageName.valueOfLabel(allYouCanDrink));
  }

  @Test
  void valueOfLabel_usingInvalidString_shouldThrowCustomException() {
    String invalidString = "";
    assertThrows(InvalidPackageNameException.class, () -> PackageName.valueOfLabel(invalidString));
  }
}
