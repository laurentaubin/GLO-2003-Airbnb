package application.bed;

import domain.bed.Bed;
import domain.bed.enums.BedType;
import domain.bed.enums.BloodType;
import domain.bed.enums.CleaningFrequency;
import java.util.HashMap;
import java.util.Map;

public class BedStarCalculator {

  private static Map<CleaningFrequency, Double> cleaningFrequencyScoreMap =
      getCleaningFrequencyMap();
  private static Map<BloodType, Double> bloodTypeScoreMap = getBloodTypeMap();
  private static Map<BedType, Integer> bedTypeScoreMap = getBedTypeMap();

  private static Map<BloodType, Double> getBloodTypeMap() {
    Map<BloodType, Double> bloodTypeMap = new HashMap<>();
    BloodType[] bloodTypes = BloodType.values();
    double[] scores = new double[] {1.5, 1.0, 0.6, 0.5, 0.5, 0.4, 0.2, 0.1};

    for (int i = 0; i < scores.length; i++) {
      bloodTypeMap.put(bloodTypes[i], scores[i]);
    }
    return bloodTypeMap;
  }

  private static Map<CleaningFrequency, Double> getCleaningFrequencyMap() {
    Map<CleaningFrequency, Double> cleaningFrequencyMap = new HashMap<>();
    CleaningFrequency[] cleaningFrequencies = CleaningFrequency.values();
    double[] scores = new double[] {0.5, 1.0, 1.25, 2.0};

    for (int i = 0; i < scores.length; i++) {
      cleaningFrequencyMap.put(cleaningFrequencies[i], scores[i]);
    }
    return cleaningFrequencyMap;
  }

  private static Map<BedType, Integer> getBedTypeMap() {
    Map<BedType, Integer> bedTypeMap = new HashMap<>();
    BedType[] bedTypes = BedType.values();
    int[] scores = new int[] {250, 500, 750};

    for (int i = 0; i < scores.length; i++) {
      bedTypeMap.put(bedTypes[i], scores[i]);
    }
    return bedTypeMap;
  }

  public static int calculateStars(Bed bed) {
    double globalScore = getGlobalScore(bed);
    return getStarsFromScore(globalScore);
  }

  private static int getStarsFromScore(double globalScore) {
    if (globalScore < 100) {
      return 1;
    }
    if (globalScore < 187.5) {
      return 2;
    }
    if (globalScore < 300) {
      return 3;
    }
    if (globalScore < 500) {
      return 4;
    }
    return 5;
  }

  private static double getGlobalScore(Bed bed) {
    double cleaningFrequencyScore = getCleaningFrequencyScore(bed.getCleaningFrequency());
    double averageBloodTypeScore = getAverageBloodTypeScore(bed.getBloodTypes());
    int bedTypeScore = getBedTypeScore(bed.getBedType());

    return cleaningFrequencyScore * averageBloodTypeScore * bedTypeScore;
  }

  private static double getCleaningFrequencyScore(CleaningFrequency cleaningFrequency) {
    return cleaningFrequencyScoreMap.get(cleaningFrequency);
  }

  private static int getBedTypeScore(BedType bedType) {
    return bedTypeScoreMap.get(bedType);
  }

  private static double getAverageBloodTypeScore(BloodType[] bloodTypes) {
    double score = 0;
    for (BloodType type : bloodTypes) {
      score += getBloodTypeScore(type);
    }
    return score / bloodTypes.length;
  }

  private static double getBloodTypeScore(BloodType bloodType) {
    return bloodTypeScoreMap.get(bloodType);
  }
}
