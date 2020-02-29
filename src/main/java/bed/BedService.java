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

  public ArrayList<Bed> getAllBeds() {
    return new ArrayList<Bed>(beds.values());
  }

  public ArrayList<Bed> Get(Query query) {
    ArrayList<Bed> filteredBeds = new ArrayList<>();
    for (Bed bed : getAllBeds()) {
      if ((!Collections.disjoint(
              query.getPackageNames(),
              bed.getPackagesNames())) // any des packages dans query est dans bed.getpackages
          && (Arrays.asList(bed.getBloodTypes())
              .containsAll(Arrays.asList(query.getBloodTypes()))) //
          && (Arrays.asList(query.getCleaningFrequencies()).contains(bed.getCleaningFrequency()))
          && (Arrays.asList(query.getBedTypes()).contains(bed.getBedType()))
          && (bed.getCapacity() >= query.getMinCapacity())) {
        filteredBeds.add(bed);
      }
    }
    return filteredBeds;
  }
}
