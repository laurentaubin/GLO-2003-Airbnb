package bed;

import static org.junit.jupiter.api.Assertions.*;

import bed.booking.Booking;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CancelationValidatorTest {
  private CancelationValidator cancelationValidator;
  private Booking booking;
  private String tenantPublicKey =
      "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
  private String arrivalDate = "2020-05-21";
  private int numberOfNights = 3;
  private String bedPackage = "allYouCanDrink";

  @BeforeEach
  void setup() {
    this.cancelationValidator = new CancelationValidator();
    this.booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);
  }

  @Test
  void
      isCancelationDateAtLeastOneDayBeforeArrivalDate_whenCancelationDateIsSevenDaysBefore_shouldBeTrue() {
    LocalDate cancelationDate = LocalDate.parse(arrivalDate).minusDays(7);
    this.cancelationValidator.setCancelationDate(cancelationDate);
    assertTrue(
        this.cancelationValidator.isCancelationDateAtLeastOneDayBeforeArrivalDate(this.booking));
  }

  @Test
  void
      isCancelationDateAtLeastOneDayBeforeArrivalDate_whenCancelationDateIsTwoDaysBefore_shouldBeTrue() {
    LocalDate cancelationDate = LocalDate.parse(arrivalDate).minusDays(2);
    System.out.println(cancelationDate.toString());
    this.cancelationValidator.setCancelationDate(cancelationDate);
    assertTrue(
        this.cancelationValidator.isCancelationDateAtLeastOneDayBeforeArrivalDate(this.booking));
  }

  @Test
  void
      isCancelationDateAtLeastOneDayBeforeArrivalDate_whenCancelationDateIsWithin24hoursBefore_shouldBeFalse() {
    LocalDate cancelationDate = LocalDate.parse("2020-05-20");
    this.cancelationValidator.setCancelationDate(cancelationDate);
    assertFalse(
        this.cancelationValidator.isCancelationDateAtLeastOneDayBeforeArrivalDate(this.booking));
  }

  @Test
  void
      isCancelationDateAtLeastOneDayBeforeArrivalDate_whenCancelationDateIsTheSameDateAsArrivalDate_shouldBeFalse() {
    LocalDate cancelationDate = LocalDate.parse("2020-05-21");
    this.cancelationValidator.setCancelationDate(cancelationDate);
    assertFalse(
        this.cancelationValidator.isCancelationDateAtLeastOneDayBeforeArrivalDate(this.booking));
  }
}
