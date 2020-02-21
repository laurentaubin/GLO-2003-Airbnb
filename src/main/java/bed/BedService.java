package bed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BedService {
  private Map<String, Bed> beds = new HashMap<>();

  public BedService() {};

  public int getTotalNumberOfBeds() {
    return beds.size();
  }

  public String addBed(Bed bed) {
    String uuid = UUID.randomUUID().toString();
    beds.put(uuid, bed);
    return uuid;
  }

  public List<Bed> getAllBeds() {
    return new ArrayList<Bed>(beds.values());
  }
}
