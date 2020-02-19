package Bed;

import java.util.ArrayList;

public class BedService {
  private ArrayList<Bed> beds = new ArrayList<Bed>();

  public void add(Bed newBed) {
    beds.add(newBed);
  }

  public ArrayList<Bed> getBeds() {
    return this.beds;
  }
}
