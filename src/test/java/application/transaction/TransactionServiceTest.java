package application.transaction;

import static org.junit.jupiter.api.Assertions.*;

import domain.bed.Bed;
import domain.booking.Booking;
import domain.transaction.TransactionService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionServiceTest {
  private TransactionService transactionService;

  private BookingTestObject bookingTestObject = new BookingTestObject();
  private Booking booking;

  private BedTestObject bedTestingObject = new BedTestObject();
  private Bed bed;

  @BeforeEach
  void setup() {
    this.booking = bookingTestObject.getBooking();
    this.bed = this.bedTestingObject.getBed();
    this.transactionService = TransactionService.getInstance();
  }

  @Test
  void getOwnerTimestamp_whenUsingValidBooking_shouldReturnEndOfDayOfTheLastDay() {
    String timestamp = this.transactionService.getEndOfBookingTimestamp(this.booking);
    assertEquals("2021-03-29T23:59:59.999Z", timestamp);
  }

  @Test
  void
      isCancelationEligibleForTotalRefund_whenCancelationDateIsExactlyTheSameDateAsArrivalDate_shouldBeTrue() {
    this.booking.setArrivalDate(LocalDate.now().plusDays(8).toString());
    assertTrue(this.transactionService.isCancelationEligibleForTotalRefund(this.booking));
  }

  @Test
  void
      isCancelationEligibleForTotalRefund_whenCancelationDateIsExactlySevenDaysBeforeArrival_shouldBeFalse() {
    this.booking.setArrivalDate(LocalDate.now().plusDays(7).toString());
    assertFalse(this.transactionService.isCancelationEligibleForTotalRefund(this.booking));
  }

  @Test
  void
      isCancelationEligibleForTotalRefund_whenCancelationDateIsAfterTheTotalRefundLimitDate_shouldBeFalse() {
    this.booking.setArrivalDate(LocalDate.now().plusDays(6).toString());
    assertFalse(this.transactionService.isCancelationEligibleForTotalRefund(this.booking));
  }

  @Test
  void
      isCancelationEligibleForPartialRefund_whenCancelationIsExactlySevenDaysBeforeArrival_shouldBeTrue() {
    this.booking.setArrivalDate(LocalDate.now().plusDays(7).toString());
    assertTrue(this.transactionService.isCancelationEligibleForPartialRefund(this.booking));
  }

  @Test
  void
      isCancelationEligibleForPartialRefund_whenCancelationDateIsExactlyThePartialRefundLimitDate_shouldBeTrue() {
    this.booking.setArrivalDate(LocalDate.now().plusDays(2).toString());
    assertTrue(this.transactionService.isCancelationEligibleForPartialRefund(this.booking));
  }

  @Test
  void
      isCancelationEligibleForPartial_whenCancelationDateIsAfterThePartialRefundLimitDate_shouldBeFalse() {
    this.booking.setArrivalDate(LocalDate.now().plusDays(1).toString());
    assertFalse(this.transactionService.isCancelationEligibleForPartialRefund(this.booking));
  }
}
