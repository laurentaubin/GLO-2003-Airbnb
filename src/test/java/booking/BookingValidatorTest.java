package booking;

import static org.junit.jupiter.api.Assertions.*;

import bed.Bed;
import bed.BedPackage;
import bed.BedService;
import bed.BedType;
import bed.BloodType;
import bed.CleaningFrequency;
import bed.PackageName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.booking.ArrivalDate.InvalidArrivalDateException;
import exceptions.booking.BedAlreadyBookedException;
import exceptions.booking.BedPackage.InvalidBookingPackageException;
import exceptions.booking.BedPackage.PackageNotAvailableException;
import exceptions.booking.BookingService.InvalidTenantPublicKeyException;
import exceptions.booking.NumberOfNights.InvalidNumberOfNightsException;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingValidatorTest {
  private BookingValidator bookingValidator;
  private BedService bedService = BedService.getInstance();
  private BookingService bookingService = BookingService.getInstance();
  private String uuid = UUID.randomUUID().toString();
  private ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  void setup() throws IOException {
    this.bookingValidator = new BookingValidator();
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.LATEX;
    CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
    BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(PackageName.BLOOD_THIRSTY, 12.5),
          new BedPackage(PackageName.ALL_YOU_CAN_DRINK, 6)
        };
    int capacity = 950;
    Bed bed =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
    bed.setUuid(this.uuid);
    this.bedService.addBed(bed, this.uuid);

    String firstReservationJson =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-21\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    Booking firstBooking =
        new JsonToBookingConverter().generateBookingFromJson(firstReservationJson);
    this.bookingService.addBooking(firstBooking);
    this.bookingService.addBookingForSpecificBed(this.uuid, firstBooking);
  }

  @AfterEach
  void cleanUp() {
    this.bedService.removeBed(this.uuid);
    this.bookingService.clearAll();
  }

  @Test
  void validateBooking_whenBookingRequestIsValid_shouldNotThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2020-05-21\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    assertDoesNotThrow(() -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenTenantPublicKeyIsMissing_shouldThrow() {
    String jsonString =
        "{"
            + "\"arrivalDate\": \"2020-05-21\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenTenantPublicKeyIsNull_shouldThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": null,"
            + "\"arrivalDate\": \"2020-05-21\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenArrivalDateIsMissing_shouldThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    assertThrows(
        InvalidArrivalDateException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenArrivalDateIsNull_shouldThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": null,"
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    assertThrows(
        InvalidArrivalDateException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenNumberOfNightsIsMissing_shouldThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2020-05-21\","
            + "\"package\": \"allYouCanDrink\""
            + "}";
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenNumberOfNightsIsNull_shouldThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2020-05-21\","
            + "\"numberOfNights\": null,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenPackageIsMissing_shouldThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2020-05-21\","
            + "\"numberOfNights\": 3"
            + "}";
    assertThrows(
        InvalidBookingPackageException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateBooking_whenPackageIsNull_shouldThrow() {
    String jsonString =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2020-05-21\","
            + "\"numberOfNights\": 3,"
            + "\"package\": null"
            + "}";
    assertThrows(
        InvalidBookingPackageException.class,
        () -> bookingValidator.validateBooking(jsonString, this.uuid));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsValid_shouldNotThrow() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    assertDoesNotThrow(() -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.uuid));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsTooShort_shouldThrow() {
    String tenantPublicKey = "72001343BA93508E74E3B593C2016D0434CF0AA76CB3DF64F93170D60EC";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.uuid));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsTooLong_shouldThrow() {
    String tenantPublicKey =
        "72001343BA93508E74E3B593C2JKBASHJFASDBL016D0434CF0AA76CB3DF64F93170D60EC";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.uuid));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsNotAlphanumeric_shouldThrow() {
    String tenantPublicKey = "72001343&A93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.uuid));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsTheSameAsOwner_shouldThrow() {
    String tenantPublicKey = this.uuid;
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.uuid));
  }

  @Test
  void validateArrivalDate_whenArrivalDateIsFormattedCorrectly_shouldNotThrow() {
    assertDoesNotThrow(() -> bookingValidator.validateArrivalDateFormat("2021-10-02"));
  }

  @Test
  void validateArrivalDate_whenArrivalDateIsNotFormattedCorrectly_shouldThrow() {
    assertThrows(
        InvalidArrivalDateException.class,
        () -> bookingValidator.validateArrivalDateFormat("2021-28-02"));
    assertThrows(
        InvalidArrivalDateException.class,
        () -> bookingValidator.validateArrivalDateFormat("2021/02/28"));
    assertThrows(
        InvalidArrivalDateException.class,
        () -> bookingValidator.validateArrivalDateFormat("21-02-28"));
  }

  @Test
  void validateArrivalDate_whemArrivalDateIsInThePast_shouldThrow() {
    assertThrows(
        InvalidArrivalDateException.class,
        () -> bookingValidator.validateArrivalDateFormat("2020-02-28"));
  }

  @Test
  void validateNumberOfNights_whenNumberOfNightsIsValid_shouldNotThrow()
      throws JsonProcessingException {
    JsonNode numberOfNightsNode = mapper.readTree("{\"numberOfNights\": 3}").get("numberOfNights");
    assertDoesNotThrow(() -> bookingValidator.validateNumberOfNights(numberOfNightsNode));
  }

  @Test
  void validateNumberOfNights_whenNumberOfNightsIsAString_shouldThrow()
      throws JsonProcessingException {
    JsonNode numberOfNightsNode =
        mapper.readTree("{\"numberOfNights\": \"3\"}").get("numberOfNights");
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingValidator.validateNumberOfNights(numberOfNightsNode));
  }

  @Test
  void validateNumberOfNights_whenNumberOfNightsIsLargerThan90_shouldThrow()
      throws JsonProcessingException {
    JsonNode numberOfNightsNode = mapper.readTree("{\"numberOfNights\": 91}").get("numberOfNights");
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingValidator.validateNumberOfNights(numberOfNightsNode));
  }

  @Test
  void validateNumberOfNights_whenNumberOfNightsIsZero_shouldThrow()
      throws JsonProcessingException {
    JsonNode numberOfNightsNode = mapper.readTree("{\"numberOfNights\": 0}").get("numberOfNights");
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingValidator.validateNumberOfNights(numberOfNightsNode));
  }

  @Test
  void validateNumberOfNights_whenNumberOfNightsIsNegative_shouldThrow()
      throws JsonProcessingException {
    JsonNode numberOfNightsNode = mapper.readTree("{\"numberOfNights\": -1}").get("numberOfNights");
    assertThrows(
        InvalidNumberOfNightsException.class,
        () -> bookingValidator.validateNumberOfNights(numberOfNightsNode));
  }

  @Test
  void validatePackage_whenPackageIsValid_shouldNotThrow() {
    String bedPackage = "allYouCanDrink";
    assertDoesNotThrow(() -> bookingValidator.validateBedPackage(bedPackage, this.uuid));
  }

  @Test
  void validatePackage_whenPackageAskedIsNotOffered_shouldThrow() {
    String askedPackage = "sweetTooth";
    assertThrows(
        PackageNotAvailableException.class,
        () -> bookingValidator.validateBedPackage(askedPackage, this.uuid));
  }

  @Test
  void validatePackage_whenPackageIsUnknown_shouldThrow() {
    String askedPackage = "unknown";
    assertThrows(
        PackageNotAvailableException.class,
        () -> bookingValidator.validateBedPackage(askedPackage, this.uuid));
  }

  @Test
  void validateIfThereIsConflictWithOtherBooking_whenThereIsNoConflict_shouldNotThrow()
      throws IOException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-25\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertDoesNotThrow(
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.uuid));
  }

  @Test
  void validateIfThereIsConflictWithOtherBooking_whenArrivalDateIsIncludedInDateRange_shouldThrow()
      throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-23\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertThrows(
        BedAlreadyBookedException.class,
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.uuid));
  }

  @Test
  void
      validateIfThereIsConflictWithOtherBooking_whenDepartureDateIsIncludedInDateRange_shouldThrow()
          throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-19\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertThrows(
        BedAlreadyBookedException.class,
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.uuid));
  }

  @Test
  void validateIfThereIsConflictWithOtherBooking_whenBookingIsOverlappingDateRange_shouldThrow()
      throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-18\","
            + "\"numberOfNights\": 10,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertThrows(
        BedAlreadyBookedException.class,
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.uuid));
  }

  @Test
  void validateIfThereIsConflictWithOtherBooking_whenAllDatesAreTheSame_shouldThrow()
      throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-21\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertThrows(
        BedAlreadyBookedException.class,
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.uuid));
  }
}
