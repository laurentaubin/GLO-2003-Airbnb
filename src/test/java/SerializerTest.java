import static org.junit.jupiter.api.Assertions.*;

import bed.Bed;
import bed.Bed.BedPackage;
import bed.Bed.BedType;
import bed.Bed.BloodType;
import bed.Bed.CleaningFrequency;
import org.junit.jupiter.api.Test;

// TESTS :
// dummy object should not throw
// assert valid object to JSON
// assert empty object to empty JSON

// invalid object should throw

public class SerializerTest {

  private class PublicAttributeObject {
    public String classroom = "G33";
    public int grade = 100;
  }

  private class PrivateAttributeObject {
    private String classroom = "G33";
    private int grade = 100;
  }

  private class PrivateAttributeWithGetterObject {
    private String classroom = "G33";
    private int grade = 100;

    public String getClassroom() {
      return this.classroom;
    }

    public void setClassroom(String classroom) {
      this.classroom = classroom;
    }

    public int getGrade() {
      return this.grade;
    }

    public void setGrade(int grade) {
      this.grade = grade;
    }
  }

  String ownerPublicKey = "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E";
  String zipCode = "12345";
  BedType bedType = BedType.LATEX;
  CleaningFrequency cleaningFrequency = CleaningFrequency.MONTHLY;
  BloodType[] bloodTypes = new BloodType[] {BloodType.O_NEG, BloodType.AB_POS};
  int capacity = 950;
  BedPackage[] packages =
      new BedPackage[] {
        new BedPackage(BedPackage.Name.BLOOD_THIRSTY, 12.5),
        new BedPackage(BedPackage.Name.SWEET_TOOTH, 6)
      };

  @Test
  void serialize_usingPublicAttributeObject_shouldEqualJson() throws Exception {
    PublicAttributeObject publicAttributeObject = new PublicAttributeObject();
    assertEquals(
        "{\"classroom\":\"G33\",\"grade\":100}", Serializer.dataToJson(publicAttributeObject));
  }

  @Test
  void serialize_usingPrivateAttributeWithGetterObject_shouldEqualJson() {
    PrivateAttributeWithGetterObject privateAttributeWithGetterObject =
        new PrivateAttributeWithGetterObject();
    assertEquals(
        "{\"classroom\":\"G33\",\"grade\":100}",
        Serializer.dataToJson(privateAttributeWithGetterObject));
  }

  @Test
  void serialize_usingInvalidPrivateAttributeObject_shouldThrow() {
    PrivateAttributeObject privateAttributeObject = new PrivateAttributeObject();
    assertThrows(RuntimeException.class, () -> Serializer.dataToJson(privateAttributeObject));
  }

  @Test
  void serialize_withDummyBedObject_shouldEqualJson() {
    Bed dummyBed = new Bed();
    assertEquals(
        "{\"ownerPublicKey\":null,\"zipCode\":null,\"bedType\":null,\"cleaningFrequency\":null,\"bloodTypes\":null,\"capacity\":0,\"packages\":null}",
        Serializer.dataToJson(dummyBed));
  }

  @Test
  void serialize_withValidBedObject_shouldEqualJson() {

    Bed validBed =
        new Bed(
            this.ownerPublicKey,
            this.zipCode,
            this.bedType,
            this.cleaningFrequency,
            this.bloodTypes,
            this.capacity,
            this.packages);

    assertEquals(
        "{\"ownerPublicKey\":\"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\",\"zipCode\":\"12345\",\"bedType\":\"latex\",\"cleaningFrequency\":\"monthly\",\"bloodTypes\":[\"O-\",\"AB+\"],\"capacity\":950,\"packages\":[{\"name\":\"bloodthirsty\",\"pricePerNight\":12.5},{\"name\":\"sweetTooth\",\"pricePerNight\":6.0}]}",
        Serializer.dataToJson(validBed));
  }
}
