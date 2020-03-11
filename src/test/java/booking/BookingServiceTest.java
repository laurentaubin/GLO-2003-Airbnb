package booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exceptions.booking.BookingService.InvalidUuidException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingServiceTest {
  private booking.BookingService bookingService;
  private String tenantPublicKey =
      "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
  private String arrivalDate = "2020-05-21";
  private Integer numberOfNights = 3;
  private String bedPackage = "allYouCanDrink";

  @BeforeEach
  void setUp() {
    this.bookingService = BookingService.getInstance();
  }

  @AfterEach
  void cleanUp() {
    this.bookingService.clearAll();
  }

  @Test
  void getTotalNumberOfBookings_whenNoBookingWasAdded_shouldEquals0() {
    assertEquals(0, this.bookingService.getTotalNumberOfBookings());
  }

  @Test
  void addBooking_whenCreatingOneBooking_shouldAddOneBookingToHashMap() {
    this.bookingService.addBooking(
        new Booking(this.tenantPublicKey, this.arrivalDate, this.numberOfNights, this.bedPackage));
    assertEquals(1, bookingService.getTotalNumberOfBookings());
  }

  @Test
  void addBooking_whenCreatingOneBooking_shouldBeEqualToFirstIndexOfGetAllBookings() {
    Booking booking =
        new Booking(this.tenantPublicKey, this.arrivalDate, this.numberOfNights, this.bedPackage);
    this.bookingService.addBooking(booking);
    assertEquals(booking, this.bookingService.getAllBookings().get(0));
  }

  @Test
  void getBookingByUuid_whenGettingBookingWithValidUuid_shouldEqualSameBooking()
      throws InvalidUuidException {
    Booking booking =
        new Booking(this.tenantPublicKey, this.arrivalDate, this.numberOfNights, this.bedPackage);
    String uuid = this.bookingService.addBooking(booking);
    assertEquals(booking, this.bookingService.getBookingByUuid(uuid));
  }

  @Test
  void getBookingByUuid_whenGettingBookingWithInvalidUuid_shouldThrow() {
    Booking booking =
        new Booking(this.tenantPublicKey, this.arrivalDate, this.numberOfNights, this.bedPackage);
    String uuid = this.bookingService.addBooking(booking);
    String invalidUuid = "";
    assertThrows(
        InvalidUuidException.class, () -> this.bookingService.getBookingByUuid(invalidUuid));
  }
}
