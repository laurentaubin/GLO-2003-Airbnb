package bed.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class JsonToBookingConverterTest {
  private JsonToBookingConverter jsonToBookingConverter = new JsonToBookingConverter();
  private String tenantPublicKey =
      "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
  private String arrivalDate = "2020-05-21";
  private Integer numberOfNights = 3;
  private String bedPackage = "allYouCanDrink";

  private Booking expectedBooking =
      new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);
  private String bookingJson =
      "{\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
          + " \"ownerPublicKey\": \"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\","
          + " \"arrivalDate\": \"2020-05-21\", "
          + "\"numberOfNights\": \"3\" ,"
          + " \"package\": \"allYouCanDrink\"}";

  @Test
  void deserializeBooking_withValidJson_shouldEqualTenantPublicKey() throws IOException {

    Booking actualBooking = this.jsonToBookingConverter.generateBookingFromJson(bookingJson);

    assertEquals(expectedBooking.getTenantPublicKey(), actualBooking.getTenantPublicKey());
  }

  @Test
  void deserializeBooking_withValidJson_shouldEqualArrivalDate() throws IOException {

    Booking actualBooking = this.jsonToBookingConverter.generateBookingFromJson(bookingJson);

    assertEquals(expectedBooking.getArrivalDate(), actualBooking.getArrivalDate());
  }

  @Test
  void deserializeBooking_withValidJson_shouldEqualNumberOfNights() throws IOException {

    Booking actualBooking = this.jsonToBookingConverter.generateBookingFromJson(bookingJson);

    assertEquals(expectedBooking.getNumberOfNights(), actualBooking.getNumberOfNights());
  }

  @Test
  void deserializeBooking_withValidJson_shouldEqualBedPackage() throws IOException {

    Booking actualBooking = this.jsonToBookingConverter.generateBookingFromJson(bookingJson);

    assertEquals(expectedBooking.getBedPackage(), actualBooking.getBedPackage());
  }

  @Test
  void deserializeBooking_withEmptyJson_shouldThrow() {
    String emptyJson = "{}";

    Exception exception =
        assertThrows(
            NullPointerException.class,
            () -> this.jsonToBookingConverter.generateBookingFromJson(emptyJson));
  }

  @Test
  void deserializeBooking_withMissingJsonField_shouldThrow() {
    String noTenantPublicKeyJson =
        "{\"arrivalDate\": \"2020-05-21\", "
            + "\"numberOfNights\": \"3\" ,"
            + " \"package\": \"allYouCanDrink\"}";

    assertThrows(
        NullPointerException.class,
        () -> this.jsonToBookingConverter.generateBookingFromJson(noTenantPublicKeyJson));
  }
}
