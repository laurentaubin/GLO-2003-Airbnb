package bed;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class JsonToBedConverterTest {
  private JsonToBedConverter jsonToBedConverter = new JsonToBedConverter();
  private String ownerPublicKey =
      "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  private String zipCode = "12345";
  private BedType bedType = BedType.LATEX;
  private CleaningFrequency cleaningFrequency = CleaningFrequency.ANNUAL;
  private BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
  private int capacity = 234;
  private BedPackage[] packages =
      new BedPackage[] {
        new BedPackage(PackageName.BLOOD_THIRSTY, 12.5), new BedPackage(PackageName.SWEET_TOOTH, 6)
      };

  private Bed expectedBed =
      new Bed(ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
  private String bedJson =
      "{\"ownerPublicKey\": \"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\", \"zipCode\": \"12345\", \"bedType\": \"latex\", \"cleaningFrequency\": \"annual\" , \"bloodTypes\": [\"O-\", \"AB+\"], \"capacity\": 234, \"packages\": [{\"name\": \"bloodthirsty\", \"pricePerNight\": 12.50}, {\"name\": \"sweetTooth\", \"pricePerNight\": 6}]}";

  @Test
  void deserializeBed_withValidJson_shouldEqualPublicKey() throws IOException {

    Bed actualBed = this.jsonToBedConverter.generateBedFromJson(bedJson);

    assertEquals(expectedBed.fetchOwnerPublicKey(), actualBed.fetchOwnerPublicKey());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualZipCode() throws IOException {

    Bed actualBed = this.jsonToBedConverter.generateBedFromJson(bedJson);

    assertEquals(expectedBed.getZipCode(), actualBed.getZipCode());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualBedType() throws IOException {

    Bed actualBed = this.jsonToBedConverter.generateBedFromJson(bedJson);

    assertEquals(expectedBed.getBedType(), actualBed.getBedType());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualCleaningFrequency() throws IOException {

    Bed actualBed = this.jsonToBedConverter.generateBedFromJson(bedJson);

    assertEquals(expectedBed.getCleaningFrequency(), actualBed.getCleaningFrequency());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualBloodTypes() throws IOException {

    Bed actualBed = this.jsonToBedConverter.generateBedFromJson(bedJson);

    assertArrayEquals(expectedBed.getBloodTypes(), actualBed.getBloodTypes());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualCapacity() throws IOException {

    Bed actualBed = this.jsonToBedConverter.generateBedFromJson(bedJson);

    assertEquals(expectedBed.getCapacity(), actualBed.getCapacity());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualPackages() throws IOException {

    Bed actualBed = this.jsonToBedConverter.generateBedFromJson(bedJson);

    assertArrayEquals(expectedBed.getPackages(), actualBed.getPackages());
  }

  @Test
  void deserializeBed_withEmptyJson_shouldThrow() {
    String emptyJson = "{}";

    Exception exception =
        assertThrows(
            NullPointerException.class,
            () -> this.jsonToBedConverter.generateBedFromJson(emptyJson));
  }

  @Test
  void deserializeBed_withMissingJsonField_shouldThrow() {
    String noZipCodeJson =
        "{\"ownerPublicKey\": \"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\", \"bedType\": \"latex\", \"cleaningFrequency\": \"annual\" , \"bloodTypes\": [\"O-\", \"AB+\"], \"capacity\": 234, \"packages\": [{\"name\": \"bloodthirsty\", \"pricePerNight\": 12.50}, {\"name\": \"sweetTooth\", \"pricePerNight\": 6}]}";

    assertThrows(
        NullPointerException.class,
        () -> this.jsonToBedConverter.generateBedFromJson(noZipCodeJson));
  }
}
