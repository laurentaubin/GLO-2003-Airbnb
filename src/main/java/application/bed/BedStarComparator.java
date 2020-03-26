package application.bed;

import domain.bed.Bed;
import java.util.Comparator;

public class BedStarComparator implements Comparator<Bed> {
  @Override
  public int compare(Bed bed1, Bed bed2) {
    return BedStarCalculator.calculateStars(bed1) - BedStarCalculator.calculateStars(bed2);
  }
}
