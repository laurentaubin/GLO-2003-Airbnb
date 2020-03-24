package application.bed;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.bed.enums.BedType;
import domain.bed.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BedValidatorTest {
  private BedValidator bedValidator;
  private String jsonString;
  private ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  void setup() {
    this.bedValidator = new BedValidator();
  }

  @Test
  void validateBed_whenCheckingValidJsonRequest_shouldNotThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertDoesNotThrow(() -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenPublicOwnerKeyIsMissing_shouldThrow() {
    jsonString =
        "{"
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidOwnerKeyException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenPublicOwnerKeyIsNull_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": null,"
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidOwnerKeyException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenZipCodeIsMissing_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidZipCodeException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenZipCodeIsNull_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": null,"
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidZipCodeException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenBedTypeIsMissing_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidBedTypeException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenBedTypeIsNull_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": null, "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidBedTypeException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenCleaningFrequencyIsMissing_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(
        InvalidCleaningFrequencyException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenCleaningFrequencyIsNull_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": null, "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(
        InvalidCleaningFrequencyException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenBloodTypesIsMissing_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidBloodTypeException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenBloodTypesIsNull_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": null,"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidBloodTypeException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenCapacityIsMissing_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidCapacityException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenCapacityIsNull_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": null,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidCapacityException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenPackagesIsMissing_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidPackageNameException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenPackagesIsNull_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": null,"
            + "\"lodgingMode\": \"cohabitation\""
            + "}";
    assertThrows(InvalidPackageNameException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenLodgingModeIsMissing_shouldNotThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "]"
            + "}";

    assertDoesNotThrow(() -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateBed_whenLodgingModeIsEmpty_shouldThrow() {
    jsonString =
        "{"
            + "\"ownerPublicKey\": \"8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E\","
            + "\"zipCode\": \"12345\","
            + "\"bedType\": \"springs\", "
            + "\"cleaningFrequency\": \"annual\", "
            + "\"bloodTypes\": [\"O-\", \"O+\"],"
            + "\"capacity\": 1,"
            + "\"packages\": ["
            + "{ \"name\": \"bloodthirsty\", \"pricePerNight\": 12.25}"
            + "],"
            + "\"lodgingMode\": \"\""
            + "}";

    assertThrows(InvalidLodgingModeException.class, () -> bedValidator.validateBed(jsonString));
  }

  @Test
  void validateOwnerPublicKey_whenKeyIsValid_shouldNotThrow() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertDoesNotThrow(() -> bedValidator.validateOwnerPublicKey(ownerPublicKey));
  }

  @Test
  void validateOwnerPublicKey_whenKeyShorterThan64Char_shouldThrow() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC72AC110BB46A3A4E";
    assertThrows(
        InvalidOwnerKeyException.class, () -> bedValidator.validateOwnerPublicKey(ownerPublicKey));
  }

  @Test
  void validateOwnerPublicKey_whenKeyLongerThan64Char_shouldThrow() {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BA3B46A3A4E";
    assertThrows(
        InvalidOwnerKeyException.class, () -> bedValidator.validateOwnerPublicKey(ownerPublicKey));
  }

  @Test
  void validateOwnerPublicKey_whenKeyContainsSpecialCharacters_shouldThrow() {
    String ownerPublicKey = "8F0436A6FB049&85B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    assertThrows(
        InvalidOwnerKeyException.class, () -> bedValidator.validateOwnerPublicKey(ownerPublicKey));
  }

  @Test
  void validateZipCode_whenZipCodeIsValid_shouldNotThrow() {
    String zipCode = "12345";
    assertDoesNotThrow(() -> bedValidator.validateZipCode(zipCode));
  }

  @Test
  void validateZipCode_whenZipCodeContainsLetters_shouldThrow() {
    String zipCode = "12A45";
    assertThrows(InvalidZipCodeException.class, () -> bedValidator.validateZipCode(zipCode));
  }

  @Test
  void validateZipCode_whenZipCodeIsLessThanFiveNumbers_shouldThrow() {
    String zipCode = "1234";
    assertThrows(InvalidZipCodeException.class, () -> bedValidator.validateZipCode(zipCode));
  }

  @Test
  void validateZipCode_whenZipCodeIsMoreThanFiveNumbers_shouldThrow() {
    String zipCode = "123456";
    assertThrows(InvalidZipCodeException.class, () -> bedValidator.validateZipCode(zipCode));
  }

  @Test
  void validateCapacity_whenCapacityIsValid_shouldNotThrow() throws JsonProcessingException {
    String capacityString = "{\"capacity\": 1}";
    String bedType = BedType.LATEX.toString();
    JsonNode capacityNode = mapper.readTree(capacityString).get("capacity");
    assertDoesNotThrow(() -> bedValidator.validateCapacity(capacityNode, bedType));
  }

  @Test
  void validateCapacity_whenNegativeCapacity_shouldThrow() throws JsonProcessingException {
    String capacityString = "{\"capacity\": -1}";
    String bedType = BedType.LATEX.toString();
    JsonNode capacityNode = mapper.readTree(capacityString).get("capacity");
    assertThrows(
        InvalidCapacityException.class, () -> bedValidator.validateCapacity(capacityNode, bedType));
  }

  @Test
  void validateCapacity_whenCapacityIsDoubleType_shouldThrow() throws JsonProcessingException {
    String capacityString = "{\"capacity\": 12.25}";
    String bedType = BedType.LATEX.toString();
    JsonNode capacityNode = mapper.readTree(capacityString).get("capacity");
    assertThrows(
        InvalidCapacityException.class, () -> bedValidator.validateCapacity(capacityNode, bedType));
  }

  @Test
  void validateCapacity_whenCapacityValueIsAString_shouldThrow() throws JsonProcessingException {
    String capacityString = "{\"capacity\": \"1\"}";
    String bedType = BedType.LATEX.toString();
    JsonNode capacityNode = mapper.readTree(capacityString).get("capacity");
    assertThrows(
        InvalidCapacityException.class, () -> bedValidator.validateCapacity(capacityNode, bedType));
  }

  @Test
  void validateCapacity_whenCapacityExceedsLatexAccommodation_shouldThrow()
      throws JsonProcessingException {
    String capacityString = "{\"capacity\": 401}";
    String bedType = BedType.LATEX.toString();
    JsonNode capacityNode = mapper.readTree(capacityString).get("capacity");
    assertThrows(
        ExceedingAccommodationCapacityException.class,
        () -> bedValidator.validateCapacity(capacityNode, bedType));
  }

  @Test
  void validateCapacity_whenCapacityExccedsMemoryFoamAccommodation_shouldThrow()
      throws JsonProcessingException {
    String capacityString = "{\"capacity\": 800}";
    String bedType = BedType.MEMORY_FOAM.toString();
    JsonNode capacityNode = mapper.readTree(capacityString).get("capacity");
    assertThrows(
        ExceedingAccommodationCapacityException.class,
        () -> bedValidator.validateCapacity(capacityNode, bedType));
  }

  @Test
  void validateCapacity_whenCapacityExceedsSpringsAccomodation_shouldThrow()
      throws JsonProcessingException {
    String capacityString = "{\"capacity\": 1004}";
    String bedType = BedType.SPRINGS.toString();
    JsonNode capacityNode = mapper.readTree(capacityString).get("capacity");
    assertThrows(
        ExceedingAccommodationCapacityException.class,
        () -> bedValidator.validateCapacity(capacityNode, bedType));
  }

  @Test
  void validateBedType_whenBedTypeIsUnknown_shouldThrow() {
    String unknownBedType = "laex";
    assertThrows(InvalidBedTypeException.class, () -> bedValidator.validateBedType(unknownBedType));
  }

  @Test
  void validateCleaningFrequency_whenCleaningFrequencyIsUnknown_shouldThrow() {
    String unknownCleaningFrequency = "anual";
    assertThrows(
        InvalidCleaningFrequencyException.class,
        () -> bedValidator.validateCleaningFrequency(unknownCleaningFrequency));
  }

  @Test
  void validateBloodTypes_whenBloodTypeIsNotAnArray_shouldThrow() throws JsonProcessingException {
    String bloodTypes = "{\"bloodTypes\": \"O+\"}";
    JsonNode bloodTypesNode = mapper.readTree(bloodTypes).get("bloodTypes");
    assertThrows(
        InvalidBloodTypeException.class, () -> bedValidator.validateBloodTypes(bloodTypesNode));
  }

  @Test
  void validateBloodTypes_whenArrayIsEmpty_shouldThrow() throws JsonProcessingException {
    String bloodTypes = "{\"bloodTypes\": []}";
    JsonNode bloodTypesNode = mapper.readTree(bloodTypes).get("bloodTypes");
    assertThrows(
        InvalidBloodTypeException.class, () -> bedValidator.validateBloodTypes(bloodTypesNode));
  }

  @Test
  void validateBloodTypes_whenBloodTypesIsUnknown_shouldThrow() throws JsonProcessingException {
    String bloodTypes = "{\"bloodTypes\": [\"OA\"]}";
    JsonNode bloodTypesNode = mapper.readTree(bloodTypes).get("bloodTypes");
    assertThrows(
        InvalidBloodTypeException.class, () -> bedValidator.validateBloodTypes(bloodTypesNode));
  }

  @Test
  void validateBedPackages_whenPackagesIsNotAnArray_shouldThrow() throws JsonProcessingException {
    String packages = "{\"packages\": \"bloodThirsty, pricePerNight: 1.00\"}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidPackageNameException.class, () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenPackageIsWithoutName_shouldThrow() throws JsonProcessingException {
    String packages = "{\"packages\": [{\"pricePerNight\": 12.25}]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidPackageNameException.class, () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenPackageIsWithoutPricePerNight_shouldThrow()
      throws JsonProcessingException {
    String packages = "{\"packages\": [{\"name\":\"bloodThirsty\"}]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidPackageNameException.class, () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOfferingSweetToothWithAllOtherPackages_shouldNotThrow()
      throws JsonProcessingException {
    String packages =
        "{\"packages\": [{\"name\":\"sweetTooth\", \"pricePerNight\":12.25},"
            + "{\"name\":\"allYouCanDrink\", \"pricePerNight\":7.75},"
            + "{\"name\":\"bloodthirsty\", \"pricePerNight\":2.33}"
            + "]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertDoesNotThrow(() -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOfferingSweetToothWithoutOtherPackages_shouldThrow()
      throws JsonProcessingException {
    String packages = "{\"packages\": [{\"name\":\"sweetTooth\", \"pricePerNight\":12.25}" + "]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidSweetToothPackageException.class,
        () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOfferingSweetToothWithoutAllYouCanDrink_shouldThrow()
      throws JsonProcessingException {
    String packages =
        "{\"packages\": [{\"name\":\"sweetTooth\", \"pricePerNight\":12.25},"
            + "{\"name\":\"bloodthirsty\", \"pricePerNight\":2.33}"
            + "]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidSweetToothPackageException.class,
        () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOfferingSweetToothWithoutBloodThirsty_shouldThrow()
      throws JsonProcessingException {
    String packages =
        "{\"packages\": [{\"name\":\"sweetTooth\", \"pricePerNight\":12.25},"
            + "{\"name\":\"allYouCanDrink\", \"pricePerNight\":2.33}"
            + "]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidSweetToothPackageException.class,
        () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOfferingAllYouCanDrinkWithBloodThirsty_shouldNotThrow()
      throws JsonProcessingException {
    String packages =
        "{\"packages\": [{\"name\":\"allYouCanDrink\", \"pricePerNight\":12.25},"
            + "{\"name\":\"bloodthirsty\", \"pricePerNight\":2.33}"
            + "]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertDoesNotThrow(() -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOfferingAllYouCanDrinkWithoutBloodThirsty_shouldThrow()
      throws JsonProcessingException {
    String packages = "{\"packages\": [{\"name\":\"allYouCanDrink\", \"pricePerNight\":12.25}]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidAllYouCanDrinkException.class, () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOnlyOfferingBloodthirsty_shouldNotThrow()
      throws JsonProcessingException {
    String packages = "{\"packages\": [{\"name\":\"bloodthirsty\", \"pricePerNight\":12.25}]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertDoesNotThrow(() -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenPackagesArrayIsEmpty_shouldThrow() throws JsonProcessingException {
    String packages = "{\"packages\": []}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidPackageNameException.class, () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateBedPackages_whenOnePackagesIsPresentMoreThanOnce_shouldThrow()
      throws JsonProcessingException {
    String packages =
        "{\"packages\": [{\"name\":\"bloodthirsty\", \"pricePerNight\":12.25},"
            + "{\"name\":\"bloodthirsty\", \"pricePerNight\":12.25}"
            + "]}";
    JsonNode packagesNode = mapper.readTree(packages).get("packages");
    assertThrows(
        InvalidPackageNameException.class, () -> bedValidator.validateBedPackages(packagesNode));
  }

  @Test
  void validateLodgingMode_whenProvidingValidLodgingMode_shouldNotThrow()
      throws JsonProcessingException {
    String lodgingModeString = "{\"lodgingMode\": \"cohabitation\"}";
    String lodgingMode = mapper.readTree(lodgingModeString).get("lodgingMode").textValue();
    assertDoesNotThrow(() -> bedValidator.validateLodgingMode(lodgingMode));
  }

  @Test
  void validateLodgingMode_whenProvidingInvalidLodgingMode_shouldThrow()
      throws JsonProcessingException {
    String lodgingModeString = "{\"lodgingMode\": \"partyOfTwo\"}";
    String lodgingMode = mapper.readTree(lodgingModeString).get("lodgingMode").textValue();
    assertThrows(
        InvalidLodgingModeException.class, () -> bedValidator.validateLodgingMode(lodgingMode));
  }
}
