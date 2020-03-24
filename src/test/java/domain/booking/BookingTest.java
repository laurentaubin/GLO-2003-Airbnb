package domain.booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingTest {
  private Booking booking;
  private String tenantPublicKey =
      "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
  private String arrivalDate = "2020-05-21";
  private int numberOfNights = 3;
  private String bedPackage = "allYouCanDrink";

  @BeforeEach
  void setup() {
    this.booking = new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);
  }

  @Test
  void createBooking_WithValidParameters_shouldNotThrow() {
    assertDoesNotThrow(() -> new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage));
  }

  @Test
  void createBooking_withValidParameters_shouldSetBookingStatusAsBooked() {
    assertEquals(BookingStatus.BOOKED.getLabel(), this.booking.getBookingStatus());
  }

  @Test
  void getBookingStatus_whenBookingHasBeenCanceled_shouldSetBookingStatusAsCanceled() {
    this.booking.cancelBooking();
    String bookingStatus = this.booking.getBookingStatus();
    assertEquals(BookingStatus.CANCELED.getLabel(), bookingStatus);
  }

  @Test
  void getBookingStatus_whenBookingHasBeenCanceled_shouldNotLetBookingStatusAsBooked() {
    this.booking.cancelBooking();
    String bookingStatus = this.booking.getBookingStatus();
    assertNotEquals(BookingStatus.BOOKED.getLabel(), bookingStatus);
  }
}
