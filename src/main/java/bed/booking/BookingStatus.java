package bed.booking;

public enum BookingStatus {
  BOOKED("booked"),
  CANCELED("canceled");

  private String label;

  BookingStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }
}
