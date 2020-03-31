package domain.bed.enums;

import domain.bed.exception.InvalidCleaningFrequencyException;

public enum CleaningFrequency {
  WEEKLY("weekly"),
  MONTHLY("monthly"),
  ANNUAL("annual"),
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
    throw new InvalidCleaningFrequencyException();
  }

  @Override
  public String toString() {
    return this.label;
  }
}
