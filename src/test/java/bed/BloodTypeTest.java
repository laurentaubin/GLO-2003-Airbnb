package bed;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.bed.BloodType.InvalidBloodTypeException;
import org.junit.jupiter.api.Test;

class BloodTypeTest {
  @Test
  void valueOfLabel_usingValidString_shouldEqualLabel() {
    String aBNeg = "AB-";
    assertEquals(BloodType.AB_NEG, BloodType.valueOfLabel(aBNeg));
  }

  @Test
  void valueOfLabel_usingInvalidString_shouldThrowCustomException() {
    String invalidString = "";
    assertThrows(InvalidBloodTypeException.class, () -> BloodType.valueOfLabel(invalidString));
  }
}
