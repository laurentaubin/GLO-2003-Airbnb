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
    JsonNode bedNode;

    bedNode = parser.getCodec().readTree(parser);
    Bed bed = new Bed();

    bed.setOwnerPublicKey(bedNode.get("ownerPublicKey").textValue());
    bed.setZipCode(bedNode.get("zipCode").textValue());
    bed.setBedType(BedType.valueOfLabel(bedNode.get("bedType").textValue()));
    bed.setCleaningFrequency(
        CleaningFrequency.valueOfLabel(bedNode.get("cleaningFrequency").textValue()));
    bed.setCapacity(bedNode.get("capacity").asInt());

    BloodType[] bloodTypes = getBedBloodTypes(bedNode.get("bloodTypes"));
    bed.setBloodTypes(bloodTypes);

    BedPackage[] bedPackages;
    bedPackages = getBedPackages(bedNode.get("packages"));
    bed.setPackages(bedPackages);

    return bed;
  }

  private BloodType[] getBedBloodTypes(JsonNode bloodTypesNode) {
    BloodType[] bloodTypes = new BloodType[bloodTypesNode.size()];
    for (int i = 0; i < bloodTypes.length; i++) {
      bloodTypes[i] = BloodType.valueOfLabel(bloodTypesNode.get(i).textValue());
    }
    return bloodTypes;
  }

  private BedPackage[] getBedPackages(JsonNode packagesNode) {
    BedPackage[] bedPackages = new BedPackage[packagesNode.size()];
    for (int i = 0; i < bedPackages.length; i++) {

      PackageName name = PackageName.valueOfLabel(packagesNode.get(i).get("name").textValue());
      double pricePerNight = packagesNode.get(i).get("pricePerNight").asDouble();
      BedPackage bedPackage = new BedPackage(name, pricePerNight);
      bedPackages[i] = bedPackage;
    }
    return bedPackages;
  }
}
