package bed;

import exceptions.bed.BedService.InvalidUuidException;
import java.util.*;

public class BedService {
  private Map<String, Bed> beds = new HashMap<String, Bed>();

  public BedService() {};

  public String addBed(Bed bed) {
    String uuid = UUID.randomUUID().toString();
    beds.put(uuid, bed);
    return uuid;
  }

  public int getTotalNumberOfBeds() {
    return beds.size();
  }

  public Bed getBedByUuid(String uuid) throws InvalidUuidException {
    if (!beds.containsKey(uuid)) {
      throw new InvalidUuidException();
    }
    return beds.get(uuid);
  }

  public List<Bed> getAllBeds() {
    return new ArrayList<Bed>(beds.values());
  }

  public List<Bed> Get(Query query) {
    ArrayList<Bed> filteredBeds = new ArrayList<>();
    for (Bed bed : getAllBeds()) {
      if ((Arrays.asList(bed.getPackages())
              .contains(query.getPackageName())) // check if bedpackage.name in there ////
          && (Arrays.asList(bed.getBloodTypes()).containsAll(Arrays.asList(query.getBloodTypes())))
          && (bed.getCleaningFrequency() == query.getCleaningFrequency())
          && (bed.getBedType() == query.getBedType())
          && (bed.getCapacity() >= query.getMinCapacity())) {
        filteredBeds.add(bed);
      }
    }
    return filteredBeds;
  }
}
