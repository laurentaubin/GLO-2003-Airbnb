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

  @BeforeEach
  void setUp() {
    SimpleModule module = new SimpleModule("BedDeserializer", new Version(3, 1, 8, null));
    module.addDeserializer(Bed.class, new BedDeserializer());
    this.mapper.registerModule(module);
  }

  @Test
  void deserializeBed_withValidJson_shouldReturnBed() throws IOException {
    String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
    String zipCode = "12345";
    BedType bedType = BedType.LATEX;
    CleaningFrequency cleaningFrequency = CleaningFrequency.ANNUAL;
    BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
    int capacity = 234;
    BedPackage[] packages =
        new BedPackage[] {
          new BedPackage(BedPackage.Name.BLOOD_THIRSTY, 12.5),
          new BedPackage(BedPackage.Name.SWEET_TOOTH, 6)
        };

    Bed expectedBed =
        new Bed(
            ownerPublicKey, zipCode, bedType, cleaningFrequency, bloodTypes, capacity, packages);

    String json =
        "{\"ownerPublicKey\": \"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\", \"zipCode\": \"12345\", \"bedType\": \"latex\", \"cleaningFrequency\": \"annual\" , \"bloodTypes\": [\"O-\", \"AB+\"], \"capacity\": 234, \"packages\": [{\"name\": \"bloodthirsty\", \"pricePerNight\": 12.50}, {\"name\": \"sweetTooth\", \"pricePerNight\": 6}]}";

    Bed actualBed = this.mapper.readValue(json, Bed.class);

    assertEquals(expectedBed.getOwnerPublicKey(), actualBed.getOwnerPublicKey());
    assertEquals(expectedBed.getZipCode(), actualBed.getZipCode());
    assertEquals(expectedBed.getBedType(), actualBed.getBedType());
    assertEquals(expectedBed.getCleaningFrequency(), actualBed.getCleaningFrequency());
    assertArrayEquals(expectedBed.getBloodTypes(), actualBed.getBloodTypes());
    assertEquals(expectedBed.getCapacity(), actualBed.getCapacity());
    assertArrayEquals(expectedBed.getPackages(), actualBed.getPackages());
  }

  @Test
  void deserializeBed_withEmptyJson_shouldThrow() throws Exception {
    String json = "{}";

    // Throws a null pointer exception. Will eventually be a custom one.
    Exception exception =
        assertThrows(Exception.class, () -> this.mapper.readValue(json, Bed.class));
  }

  @Test
  void deserializeBed_withMissingJsonField_shouldThrow() throws Exception {
    // No zip code
    String json =
        "{\"ownerPublicKey\": \"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\", \"bedType\": \"latex\", \"cleaningFrequency\": \"annual\" , \"bloodTypes\": [\"O-\", \"AB+\"], \"capacity\": 234, \"packages\": [{\"name\": \"bloodthirsty\", \"pricePerNight\": 12.50}, {\"name\": \"sweetTooth\", \"pricePerNight\": 6}]}";

    // Throws a null pointer exception. Will eventually be a custom one.
    Exception exception =
        assertThrows(Exception.class, () -> this.mapper.readValue(json, Bed.class));
  }
}
