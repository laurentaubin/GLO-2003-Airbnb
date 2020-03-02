package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingValidatorTest {
  private BookingValidator bookingValidator;

  @BeforeEach
  void setup() {
    this.bookingValidator = new BookingValidator();
  }

  @Test
  void isTenantKeyValid_whenValidKey_shouldBeTrue() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertTrue(this.bookingValidator.isTenantKeyValid(ownerPublicKey));
  }

  @Test
  void isTenantKeyValid_whenInvalidKey_shouldBeFalse() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB7?933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertFalse(this.bookingValidator.isTenantKeyValid(ownerPublicKey));
  }

  @Test
  void isTenantKeyValid_whenShortKey_shouldBeFalse() {
    String ownerPublicKey = "8F043049085B7F19AB7?933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertFalse(this.bookingValidator.isTenantKeyValid(ownerPublicKey));
  }

  @Test
  void isTenantKeyValid_whenLongKey_shouldBeFalse() {
    String ownerPublicKey = "8F04123GB3049085B7F19AB7?933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertFalse(this.bookingValidator.isTenantKeyValid(ownerPublicKey));
  }

  @Test
  void isNumberOfNightsValid_whenValidNumber_shouldBeTrue() {
    int numberOfNights = 30;
    assertTrue(this.bookingValidator.isNumberOfNightsValid(numberOfNights));
  }

  @Test
  void isNumberOfNightsValid_whenInvalidNumber_shouldBeFalse() {
    int numberOfNights = 91;
    assertFalse(this.bookingValidator.isNumberOfNightsValid(numberOfNights));
  }

  @Test
  void isValidDateFormat_whenValidDate_shouldBeTrue() {
    String date = "2020-12-08";
    assertTrue(this.bookingValidator.isValidDateFormat(date));
  }

  @Test
  void isValidDateFormat_whenInvalidDate_shouldBeFalse() {
    String date = "2020:12:08";
    assertFalse(this.bookingValidator.isValidDateFormat(date));
  }
}
