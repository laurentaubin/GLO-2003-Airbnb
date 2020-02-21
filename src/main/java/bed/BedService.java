package bed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

  public Bed getBedByUuid(String uuid) {
    if (!beds.containsKey(uuid)) {
      throw new IllegalArgumentException();
    }
    return beds.get(uuid);
  }

  public List<Bed> getAllBeds() {
    return new ArrayList<Bed>(beds.values());
  }
}
