package bed;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class BedDeserializer extends JsonDeserializer<Bed> {
  BedDeserializer() {
    super();
  }

  @Override
  public Bed deserialize(JsonParser parser, DeserializationContext deserializer)
      throws IOException {

    JsonNode bedNode = parser.getCodec().readTree(parser);
    Bed bed = new Bed();

    bed.setOwnerPublicKey(bedNode.get("ownerPublicKey").textValue());
    bed.setZipCode(bedNode.get("zipCode").textValue());
    bed.setBedType(Bed.BedType.valueOfLabel(bedNode.get("bedType").textValue()));
    bed.setCleaningFrequency(
        Bed.CleaningFrequency.valueOfLabel(bedNode.get("cleaningFrequency").textValue()));
    bed.setCapacity(bedNode.get("capacity").asInt());

    Bed.BloodType[] bloodTypes = getBedBloodTypes(bedNode.get("bloodTypes"));
    bed.setBloodTypes(bloodTypes);

    Bed.BedPackage[] bedPackages = getBedPackages(bedNode.get("packages"));
    bed.setPackages(bedPackages);

    return bed;
  }

  private Bed.BloodType[] getBedBloodTypes(JsonNode bloodTypesNode) {
    Bed.BloodType[] bloodTypes = new Bed.BloodType[bloodTypesNode.size()];
    for (int i = 0; i < bloodTypes.length; i++) {
      bloodTypes[i] = Bed.BloodType.valueOfLabel(bloodTypesNode.get(i).textValue());
    }
    return bloodTypes;
  }

  private Bed.BedPackage[] getBedPackages(JsonNode packagesNode) {
    Bed.BedPackage[] bedPackages = new Bed.BedPackage[packagesNode.size()];
    for (int i = 0; i < bedPackages.length; i++) {

      Bed.BedPackage.Name name =
          Bed.BedPackage.Name.valueOfLabel(packagesNode.get(i).get("name").textValue());
      double pricePerNight = packagesNode.get(i).get("pricePerNight").asDouble();
      Bed.BedPackage bedPackage = new Bed.BedPackage(name, pricePerNight);
      bedPackages[i] = bedPackage;
    }
    return bedPackages;
  }
}
