package bed;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.bed.CleaningFrequency.InvalidCleaningFrequencyException;

public enum CleaningFrequency {
  @JsonProperty("weekly")
  WEEKLY("weekly", 0.5),
  @JsonProperty("monthly")
  MONTHLY("monthly", 1),
  @JsonProperty("annual")
  ANNUAL("annual", 1.25),
  @JsonProperty("never")
  NEVER("never", 2);

  private String label;
  private double score;

  CleaningFrequency(String frequency, double score) {
    this.label = frequency;
    this.score = score;
  }

  public static CleaningFrequency valueOfLabel(String frequency) {
    for (CleaningFrequency cleaningFrequency : values()) {
      if (cleaningFrequency.label.equals(frequency)) {
        return cleaningFrequency;
      }
    }
    throw new InvalidCleaningFrequencyException();
  }

  public double getScore() {
    return this.score;
  }

  @Override
  public String toString() {
    return this.label;
  }
}
