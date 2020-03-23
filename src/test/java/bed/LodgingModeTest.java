package bed;

import static org.junit.jupiter.api.Assertions.*;

import bed.exception.InvalidLodgingModeException;
import org.junit.jupiter.api.Test;

public class LodgingModeTest {
  @Test
  void valueOfLabel_usingValidString_shouldEqualLabel() {
    String cohabitation = "cohabitation";
    assertEquals(LodgingMode.COHABITATION, LodgingMode.valueOfLabel(cohabitation));
  }

  @Test
  void valueOfLabel_usingInvalidString_shouldThrowCustomException() {
    String invalidLodgingMode = "";
    assertThrows(
        InvalidLodgingModeException.class, () -> LodgingMode.valueOfLabel(invalidLodgingMode));
  }
}
