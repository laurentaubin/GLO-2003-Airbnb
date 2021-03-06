package domain.bed.enums;

import static org.junit.jupiter.api.Assertions.*;

import domain.bed.exception.InvalidCleaningFrequencyException;
import org.junit.jupiter.api.Test;

class CleaningFrequencyTest {
  @Test
  void valueOfLabel_usingValidString_shouldEqualLabel() {
    String never = "never";
    assertEquals(CleaningFrequency.NEVER, CleaningFrequency.valueOfLabel(never));
  }

  @Test
  void valueOfLabel_usingInvalidString_shouldThrowCustomException() {
    String invalidString = "";
    assertThrows(
        InvalidCleaningFrequencyException.class,
        () -> CleaningFrequency.valueOfLabel(invalidString));
  }
}
