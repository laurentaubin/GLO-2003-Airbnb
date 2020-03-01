package bed;

import java.util.Comparator;

public class BedStarComparator implements Comparator<Bed> {
  @Override
  public int compare(Bed bed1, Bed bed2) {
    return bed1.getNumberOfStars() - bed2.getNumberOfStars();
  }
}
