package bed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class BedService {
  private Map<String, Bed> beds = new HashMap<>();

  public BedService() {};

  public int getTotalNumberOfBeds() {
    return beds.size();
  }

  public String createBed(
      String ownerPublicKey,
      String zipCode,
      Bed.BedType bedType,
      Bed.CleaningFrequency cleaningFrequency,
      Bed.BloodType[] bloodTypes,
      int capacity,
      Bed.BedPackage[] packages) {
    Bed bed = new Bed();
    bed.setOwnerPublicKey(ownerPublicKey);
    bed.setZipCode(zipCode);
    bed.setBedType(bedType);
    bed.setCleaningFrequency(cleaningFrequency);
    bed.setBloodTypes(bloodTypes);
    bed.setCapacity(capacity);
    bed.setPackages(packages);
    String uuid = UUID.randomUUID().toString();
    beds.put(uuid, bed);
    return uuid;
  }

  public List<Bed> getAllBeds() {
    return beds.keySet().stream().sorted().map((id) -> beds.get(id)).collect(Collectors.toList());
  }
}
