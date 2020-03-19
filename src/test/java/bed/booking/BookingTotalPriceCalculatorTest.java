package bed.booking;

import static org.junit.jupiter.api.Assertions.*;

import bed.BedPackage;
import bed.PackageName;
import bed.booking.exception.InvalidNumberOfNightsException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookingTotalPriceCalculatorTest {
  public static BedPackage[] bedPackages;
  public static Booking booking;
  public static BookingTotalPriceCalculator bookingTotalPriceCalculator;

  @BeforeAll
  static void setup() {
    bedPackages =
        new BedPackage[] {
          new BedPackage(PackageName.BLOOD_THIRSTY, 10.23),
        };
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String arrivalDate = "2020-05-21";
    Integer numberOfNights = 3;
    String bedPackage = "bloodthirsty";
    booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);
    bookingTotalPriceCalculator = new BookingTotalPriceCalculator(bedPackages, booking);
  }

  @Test
  void calculateTotalPrice_whenNumberOfNightsIsLowerThanThree_shouldNotApplyDiscount() {
    booking.setNumberOfNights(2);
    BigDecimal total = bookingTotalPriceCalculator.getTotalWithDiscount();
    assertEquals(BigDecimal.valueOf(20.46).setScale(2, RoundingMode.HALF_UP), total);
  }

  @Test
  void calculateTotalPrice_whenNumberOfNightsIsBetweenThreeAndTen_shouldApplyFivePercentDiscount() {
    booking.setNumberOfNights(3);
    BigDecimal total = bookingTotalPriceCalculator.getTotalWithDiscount();
    assertEquals(BigDecimal.valueOf(29.16).setScale(2, RoundingMode.HALF_UP), total);
  }

  @Test
  void calculateTotalPrice_whenNumberOfNightsIsBetweenTenAndThirty_shouldApplyTenPercentDiscount() {
    booking.setNumberOfNights(10);
    BigDecimal total = bookingTotalPriceCalculator.getTotalWithDiscount();
    assertEquals(BigDecimal.valueOf(92.07).setScale(2, RoundingMode.HALF_UP), total);
  }

  @Test
  void calculateTotalPrice_whenNumberOfNightsIsThirtyOrMore_shouldApply25PercentDiscount() {
    booking.setNumberOfNights(50);
    BigDecimal total = bookingTotalPriceCalculator.getTotalWithDiscount();
    assertEquals(BigDecimal.valueOf(383.63).setScale(2, RoundingMode.HALF_UP), total);
  }

  @Test
  void calculateTotalPrice_whenNumberOfNightsIsZero_shouldThrow() {
    booking.setNumberOfNights(0);
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingTotalPriceCalculator.getTotalWithDiscount());
  }

  @Test
  void calculateTotalPrice_whenNumberOfNightsIsNegative_shouldThrow() {
    booking.setNumberOfNights(-1);
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingTotalPriceCalculator.getTotalWithDiscount());
  }
}
