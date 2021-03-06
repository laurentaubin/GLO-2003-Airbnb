package application.booking;

import static org.junit.jupiter.api.Assertions.*;

import application.bed.BedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.bed.Bed;
import domain.bed.enums.BedPackage;
import domain.bed.enums.BedType;
import domain.bed.enums.BloodType;
import domain.bed.enums.CleaningFrequency;
import domain.bed.enums.PackageName;
import domain.booking.Booking;
import domain.booking.exception.BedAlreadyBookedException;
import domain.booking.exception.InvalidArrivalDateException;
import domain.booking.exception.InvalidBookingPackageException;
import domain.booking.exception.InvalidNumberOfNightsException;
import domain.booking.exception.InvalidTenantPublicKeyException;
import domain.booking.exception.PackageNotAvailableException;
import domain.booking.exception.UnallowedBookingException;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.booking.JsonToBookingConverter;

public class BookingValidatorTest {
  private BookingValidator bookingValidator;
  private Bed bed;
  private BedService bedService = BedService.getInstance();
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
    this.bed =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
    this.bedService.addBed(bed);
    bed.setUuid(this.uuid);

    String firstReservationJson =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-21\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    Booking firstBooking =
        new JsonToBookingConverter().generateBookingFromJson(firstReservationJson);
    bed.addBooking(firstBooking);
  }

  @AfterEach
  void cleanUp() {
    this.bedService.removeBed(this.uuid);
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
    assertDoesNotThrow(() -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
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
        () -> bookingValidator.validateBooking(jsonString, this.bed));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsValid_shouldNotThrow() {
    String tenantPublicKey = "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    assertDoesNotThrow(() -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.bed));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsTooShort_shouldThrow() {
    String tenantPublicKey = "72001343BA93508E74E3B593C2016D0434CF0AA76CB3DF64F93170D60EC";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.bed));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsTooLong_shouldThrow() {
    String tenantPublicKey =
        "72001343BA93508E74E3B593C2JKBASHJFASDBL016D0434CF0AA76CB3DF64F93170D60EC";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.bed));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsNotAlphanumeric_shouldThrow() {
    String tenantPublicKey = "72001343&A93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
    assertThrows(
        InvalidTenantPublicKeyException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.bed));
  }

  @Test
  void validateTenantPublicKey_whenTenantPublicKeyIsTheSameAsOwner_shouldThrow() {
    String tenantPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertThrows(
        UnallowedBookingException.class,
        () -> bookingValidator.validateTenantPublicKey(tenantPublicKey, this.bed));
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
    assertDoesNotThrow(() -> bookingValidator.validateBedPackage(bedPackage, this.bed));
  }

  @Test
  void validatePackage_whenPackageAskedIsNotOffered_shouldThrow() {
    String askedPackage = "sweetTooth";
    assertThrows(
        PackageNotAvailableException.class,
        () -> bookingValidator.validateBedPackage(askedPackage, this.bed));
  }

  @Test
  void validatePackage_whenPackageIsUnknown_shouldThrow() {
    String askedPackage = "unknown";
    assertThrows(
        PackageNotAvailableException.class,
        () -> bookingValidator.validateBedPackage(askedPackage, this.bed));
  }

  @Test
  void
      validateIfThereIsConflictWithOtherBooking_whenThereIsNoConflictAndAfterOtherBooking_shouldNotThrow()
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
                arrivalDate, numberOfNights, this.bed));
  }

  @Test
  void
      validateIfThereIsConflictWithOtherBooking_whenThereIsNoConflictAndBeforeOtherBooking_shouldNotThrow()
          throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-15\","
            + "\"numberOfNights\": 4,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertDoesNotThrow(
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.bed));
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
                arrivalDate, numberOfNights, this.bed));
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
                arrivalDate, numberOfNights, this.bed));
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
                arrivalDate, numberOfNights, this.bed));
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
                arrivalDate, numberOfNights, this.bed));
  }

  @Test
  void
      validateIfThereIsConflictWithOtherBooking_whenAskedArrivalDateIsTheSameAsOtherBookingDepartureDate_shouldNotThrow()
          throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-24\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertDoesNotThrow(
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.bed));
  }

  @Test
  void
      validateIfThereIsConflictWithOtherBooking_whenAskedDepartureDateIsTheSameAsOtherBookingArrivalDate_shouldNotThrow()
          throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-18\","
            + "\"numberOfNights\": 3,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertDoesNotThrow(
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.bed));
  }

  @Test
  void
      validateIfThereIsConflictWithOtherBooking_whenAskedBookingIsCompletelyIncludedInOtherDateRange_shouldThrow()
          throws JsonProcessingException {
    String secondReservation =
        "{"
            + "\"tenantPublicKey\": \"72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC\","
            + "\"arrivalDate\": \"2021-05-22\","
            + "\"numberOfNights\": 1,"
            + "\"package\": \"allYouCanDrink\""
            + "}";
    JsonNode secondBookingNode = mapper.readTree(secondReservation);
    String arrivalDate = secondBookingNode.get("arrivalDate").textValue();
    int numberOfNights = secondBookingNode.get("numberOfNights").asInt();
    assertThrows(
        BedAlreadyBookedException.class,
        () ->
            bookingValidator.validateIfThereIsConflictWithAnotherReservation(
                arrivalDate, numberOfNights, this.bed));
  }
}
