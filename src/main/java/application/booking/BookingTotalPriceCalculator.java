package application.booking;

import domain.bed.enums.BedPackage;
import domain.booking.Booking;
import domain.booking.exception.InvalidNumberOfNightsException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BookingTotalPriceCalculator {
  private BedPackage[] bedPackages;
  private Booking booking;

  public BookingTotalPriceCalculator(BedPackage[] bedPackages, Booking booking) {
    this.bedPackages = bedPackages;
    this.booking = booking;
  }

  public BigDecimal getTotalWithDiscount() {
    if (booking.getNumberOfNights() >= 1) {
      return calculateTotal();
    } else {
      throw new InvalidNumberOfNightsException();
    }
  }

  public double getDiscount(int numberOfNights) {
    if (numberOfNights >= 3 && numberOfNights < 10) {
      return 0.05;
    } else if (numberOfNights >= 10 && numberOfNights < 30) {
      return 0.1;
    } else if (numberOfNights >= 30) {
      return 0.25;
    }
    return 0;
  }

  private BigDecimal calculateTotal() {
    BigDecimal total = BigDecimal.ZERO;
    for (BedPackage bedPackage : this.bedPackages) {
      if (bedPackage.getName().toString().equals(booking.getBedPackage())) {
        BigDecimal pricePerNight = BigDecimal.valueOf(bedPackage.getPricePerNight());
        total = pricePerNight.multiply(BigDecimal.valueOf(booking.getNumberOfNights()));
        total =
            total.subtract(
                total.multiply(BigDecimal.valueOf(getDiscount(booking.getNumberOfNights()))));
      }
    }
    return total.setScale(2, RoundingMode.HALF_UP);
  }
}
