package bed;

import exceptions.bed.BedService.InvalidUuidException;
import java.util.*;

public class BedService {
  private Map<String, Bed> beds = new HashMap<String, Bed>();
  private static BedService bedService = null;

  private BedService() {};

  public static BedService getInstance() {
    if (bedService == null) {
      bedService = new BedService();
    }
    return bedService;
  }

  public String addBed(Bed bed, String uuid) {
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

  public void clearAllBeds() {
    beds.clear();
  }

  private ArrayList<Bed> sortBeds(ArrayList<Bed> beds) {
    Collections.sort(beds, new BedStarComparator());
    Collections.reverse(beds);
    return beds;
  }

  public ArrayList<Bed> Get(Query query) {
    ArrayList<Bed> filteredBeds = new ArrayList<>();
    for (Bed bed : getAllBeds()) {
      Set<PackageName> bedPackagesNamesSet = new HashSet<>(Arrays.asList(bed.packagesNames()));
      Set<PackageName> queryPackagesNamesSet =
          new HashSet<>(Arrays.asList(query.getPackagesNames()));
      if ((!Collections.disjoint(bedPackagesNamesSet, queryPackagesNamesSet))
          && (Arrays.asList(bed.getBloodTypes()).containsAll(Arrays.asList(query.getBloodTypes())))
          && (Arrays.asList(query.getCleaningFrequencies()).contains(bed.getCleaningFrequency()))
          && (Arrays.asList(query.getBedTypes()).contains(bed.getBedType()))
          && (bed.getCapacity() >= query.getMinCapacity())) {
        filteredBeds.add(bed);
      }
    }

    ArrayList<Bed> beds = sortBeds(filteredBeds);

    return beds;
  }

  public Map<String, Bed> getBedsMap() {
    return beds;
  }
}
