package booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BookingTest {

  @Test
  void createBooking_WithValidParameters_shouldNotThrow() throws Exception {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    String arrivalDate = "2020-05-21";
    Integer numberOfNights = 3;
    String bedPackage = "allYouCanDrink";

    assertDoesNotThrow(() -> new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage));
  }
}
