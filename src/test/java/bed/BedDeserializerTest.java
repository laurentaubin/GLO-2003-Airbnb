package bed;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedDeserializerTest {
  private ObjectMapper mapper = new ObjectMapper();
  private String ownerPublicKey =
      "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  private String zipCode = "12345";
  private BedType bedType = BedType.LATEX;
  private CleaningFrequency cleaningFrequency = CleaningFrequency.ANNUAL;
  private BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
  private int capacity = 234;
  private BedPackage[] packages =
      new BedPackage[] {
        new BedPackage(BedPackage.Name.BLOOD_THIRSTY, 12.5),
        new BedPackage(BedPackage.Name.SWEET_TOOTH, 6)
      };

  private Bed expectedBed =
      new Bed(ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);
  private String bedJson =
      "{\"ownerPublicKey\": \"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\", \"zipCode\": \"12345\", \"bedType\": \"latex\", \"cleaningFrequency\": \"annual\" , \"bloodTypes\": [\"O-\", \"AB+\"], \"capacity\": 234, \"packages\": [{\"name\": \"bloodthirsty\", \"pricePerNight\": 12.50}, {\"name\": \"sweetTooth\", \"pricePerNight\": 6}]}";

  @BeforeEach
  void setUp() {
    SimpleModule module = new SimpleModule("BedDeserializer", new Version(3, 1, 8, null));
    module.addDeserializer(Bed.class, new BedDeserializer());
    this.mapper.registerModule(module);
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualPublicKey() throws IOException {

    Bed actualBed = this.mapper.readValue(bedJson, Bed.class);

    assertEquals(expectedBed.getOwnerPublicKey(), actualBed.getOwnerPublicKey());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualZipCode() throws IOException {

    Bed actualBed = this.mapper.readValue(bedJson, Bed.class);

    assertEquals(expectedBed.getZipCode(), actualBed.getZipCode());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualBedType() throws IOException {

    Bed actualBed = this.mapper.readValue(bedJson, Bed.class);

    assertEquals(expectedBed.getBedType(), actualBed.getBedType());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualCleaningFrequency() throws IOException {

    Bed actualBed = this.mapper.readValue(bedJson, Bed.class);

    assertEquals(expectedBed.getCleaningFrequency(), actualBed.getCleaningFrequency());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualBloodTypes() throws IOException {

    Bed actualBed = this.mapper.readValue(bedJson, Bed.class);

    assertArrayEquals(expectedBed.getBloodTypes(), actualBed.getBloodTypes());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualCapacity() throws IOException {

    Bed actualBed = this.mapper.readValue(bedJson, Bed.class);

    assertEquals(expectedBed.getCapacity(), actualBed.getCapacity());
  }

  @Test
  void deserializeBed_withValidJson_shouldEqualPackages() throws IOException {

    Bed actualBed = this.mapper.readValue(bedJson, Bed.class);

    assertArrayEquals(expectedBed.getPackages(), actualBed.getPackages());
  }

  @Test
  void deserializeBed_withEmptyJson_shouldThrow() {
    String emptyJson = "{}";

    Exception exception =
        assertThrows(NullPointerException.class, () -> this.mapper.readValue(emptyJson, Bed.class));
  }

  @Test
  void deserializeBed_withMissingJsonField_shouldThrow() {
    String noZipCodeJson =
        "{\"ownerPublicKey\": \"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\", \"bedType\": \"latex\", \"cleaningFrequency\": \"annual\" , \"bloodTypes\": [\"O-\", \"AB+\"], \"capacity\": 234, \"packages\": [{\"name\": \"bloodthirsty\", \"pricePerNight\": 12.50}, {\"name\": \"sweetTooth\", \"pricePerNight\": 6}]}";

    Exception exception =
        assertThrows(
            NullPointerException.class, () -> this.mapper.readValue(noZipCodeJson, Bed.class));
  }
}
