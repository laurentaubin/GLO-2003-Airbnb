package transactions;

import bed.booking.Booking;

public class BookingTestObject {
  private Booking booking;
  private String tenantPublicKey =
      "72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC";
  private String arrivalDate = "2021-03-26";
  private int numberOfNights = 3;
  private String bedPackage = "allYouCanDrink";

  public BookingTestObject() {};

  public Booking getBooking() {
    return new Booking(tenantPublicKey, arrivalDate, numberOfNights, bedPackage);
  }
}
