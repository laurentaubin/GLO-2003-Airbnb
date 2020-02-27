package bed;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CleaningFrequency {
  @JsonProperty("weekly")
  WEEKLY("weekly"),
  @JsonProperty("monthly")
  MONTHLY("monthly"),
  @JsonProperty("annual")
  ANNUAL("annual"),
  @JsonProperty("never")
  NEVER("never");

  private String label;

  CleaningFrequency(String frequency) {
    this.label = frequency;
  }

  public static CleaningFrequency valueOfLabel(String frequency) {
    for (CleaningFrequency cleaningFrequency : values()) {
      if (cleaningFrequency.label.equals(frequency)) {
        return cleaningFrequency;
      }
    }
    throw new IllegalArgumentException("Invalid cleaning frequency");
  }

  @Override
  public String toString() {
    return this.label;
  }
}
