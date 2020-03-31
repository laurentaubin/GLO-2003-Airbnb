package infrastructure.booking;

import static org.junit.jupiter.api.Assertions.*;

import domain.booking.Booking;
import domain.booking.exception.BookingNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingRepositoryTest {
  private String tenantPublicKey =
      "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
  private String arrivalDate = "2020-05-21";
  private Integer numberOfNights = 3;
  private String bedPackage = "allYouCanDrink";
  private Booking booking;
  private BookingRepository bookingRepository;
  private String bookingUuid = UUID.randomUUID().toString();

  @BeforeEach
  void setUp() {
    this.bookingRepository = new BookingRepository();
    this.booking =
        new Booking(this.tenantPublicKey, this.arrivalDate, this.numberOfNights, this.bedPackage);
  }

  @Test
  void getBooking_whenNoBookingAdded_shouldReturnEmptyBookingRepo() {
    assertEquals(0, this.bookingRepository.getAllBookings().size());
  }

  @Test
  void addBooking_whenOneValidBookingAdded_shouldAddOneBookingToRepo() {
    this.bookingRepository.addBooking(bookingUuid, this.booking);
    assertEquals(1, this.bookingRepository.getAllBookings().size());
  }

  @Test
  void addBooking_whenOneValidBookingAdded_shoudBeEqualToFirstIndexOfGetAllBookings() {
    this.bookingRepository.addBooking(this.bookingUuid, this.booking);
    assertEquals(this.booking, this.bookingRepository.getAllBookings().get(0));
  }

  @Test
  void getBooking_withGettingBookingWithValidUuid_shouldEqualSameBed() {
    this.bookingRepository.addBooking(this.bookingUuid, this.booking);
    assertEquals(this.booking, this.bookingRepository.getBooking(this.bookingUuid));
  }

  @Test
  void getBooking_withGettingBookingWithInvalidUuid_shouldThrow() {
    this.bookingRepository.addBooking(this.bookingUuid, this.booking);
    String invalidUuid = "";
    assertThrows(
        BookingNotFoundException.class, () -> this.bookingRepository.getBooking(invalidUuid));
  }
}
