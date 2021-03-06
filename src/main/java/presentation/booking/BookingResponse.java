package presentation.booking;

import domain.booking.Booking;
import java.math.BigDecimal;

public class BookingResponse {
  private String arrivalDate;
  private Integer numberOfNights;
  private String bedPackage;
  private String status;
  private BigDecimal total;

  public BookingResponse(Booking booking) {
    this.arrivalDate = booking.getArrivalDate();
    this.numberOfNights = booking.getNumberOfNights();
    this.bedPackage = booking.getBedPackage();
    this.status = booking.getBookingStatus();
    this.total = booking.getTotal();
  }

  public String getArrivalDate() {
    return this.arrivalDate;
  }

  public void setArrivalDate(String arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public Integer getNumberOfNights() {
    return this.numberOfNights;
  }

  public void setNumberOfNights(Integer numberOfNights) {
    this.numberOfNights = numberOfNights;
  }

  public String getPackage() {
    return this.bedPackage;
  }

  public void setBedPackage(String bedPackage) {
    this.bedPackage = bedPackage;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public String getStatus() {
    return this.status;
  }
}
