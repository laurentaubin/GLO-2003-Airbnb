import static org.junit.jupiter.api.Assertions.*;

import bed.*;
import exceptions.Serializer.UnserialiazableObjectException;
import org.junit.jupiter.api.Test;

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
        new BedPackage(PackageName.BLOOD_THIRSTY, 12.5), new BedPackage(PackageName.SWEET_TOOTH, 6)
      };

  @Test
  void serialize_usingPublicAttributeObject_shouldEqualJson() throws Exception {
    PublicAttributeObject publicAttributeObject = new PublicAttributeObject();
    assertEquals(
        "{\"classroom\":\"G33\",\"grade\":100}", Serializer.dataToJson(publicAttributeObject));
  }

  @Test
  void serialize_usingPrivateAttributeWithGetterObject_shouldEqualJson()
      throws UnserialiazableObjectException {
    PrivateAttributeWithGetterObject privateAttributeWithGetterObject =
        new PrivateAttributeWithGetterObject();
    assertEquals(
        "{\"classroom\":\"G33\",\"grade\":100}",
        Serializer.dataToJson(privateAttributeWithGetterObject));
  }

  @Test
  void serialize_usingInvalidPrivateAttributeObject_shouldThrow() {
    PrivateAttributeObject privateAttributeObject = new PrivateAttributeObject();
    assertThrows(
        UnserialiazableObjectException.class, () -> Serializer.dataToJson(privateAttributeObject));
  }

  @Test
  void serialize_withDummyBedObject_shouldEqualJson() throws UnserialiazableObjectException {
    Bed dummyBed = new Bed();

    assertEquals(
        "{\"ownerPublicKey\":null,\"zipCode\":null,\"bedType\":null,\"cleaningFrequency\":null,\"bloodTypes\":null,\"capacity\":0,\"packages\":null,\"stars\":-1}",
        Serializer.dataToJson(dummyBed));
  }

  @Test
  void serialize_withValidBedObject_shouldEqualJson() throws UnserialiazableObjectException {

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
        "{\"ownerPublicKey\":\"8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E\",\"zipCode\":\"12345\",\"bedType\":\"latex\",\"cleaningFrequency\":\"monthly\",\"bloodTypes\":[\"O-\",\"AB+\"],\"capacity\":950,\"packages\":[{\"name\":\"bloodthirsty\",\"pricePerNight\":12.5},{\"name\":\"sweetTooth\",\"pricePerNight\":6.0}],\"stars\":3}",
        Serializer.dataToJson(validBed));
  }
}
