package infrastructure.bed;

import domain.bed.Bed;
import domain.booking.exception.BedNotFoundException;
import java.util.*;

public class BedRepository {
  private Map<String, Bed> beds = new HashMap<>();

  public BedRepository() {};

  public void addBed(String uuid, Bed bed) {
    beds.put(uuid, bed);
  }

  public int getTotalNumberOfBeds() {
    return beds.size();
  }

  public boolean containsBed(String uuid) {
    return beds.containsKey(uuid);
  }

  public Bed getBed(String uuid) {
    if (!containsBed(uuid)) {
      throw new BedNotFoundException(uuid);
    }
    return beds.get(uuid);
  }

  public ArrayList<Bed> getAllBeds() {
    return new ArrayList<Bed>(beds.values());
  }

  public void removeBed(String uuid) {
    beds.remove(uuid);
  }

  public void clearAllBeds() {
    beds.clear();
  }
}
