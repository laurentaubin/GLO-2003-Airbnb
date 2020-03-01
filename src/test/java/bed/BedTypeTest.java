package bed;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.bed.BedType.InvalidBedTypeException;
import org.junit.jupiter.api.Test;

class BedTypeTest {
  @Test
  void valueOfLabel_usingValidString_shouldEqualLabel() {
    String latex = "latex";
    assertEquals(BedType.LATEX, BedType.valueOfLabel(latex));
  }

  @Test
  void valueOfLabel_usingInvalidString_shouldThrowCustomException() {
    String invalidString = "";
    assertThrows(InvalidBedTypeException.class, () -> BedType.valueOfLabel(invalidString));
  }
}
