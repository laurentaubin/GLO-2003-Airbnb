package domain.booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
  private String tenantPublicKey;
  private String arrivalDate;
  private Integer numberOfNights;
  private String bedPackage;
  private BookingStatus bookingStatus;
  private BigDecimal total;
  private Integer colonySize = 0;

  public void setColonySize(Integer colonySize) {
    this.colonySize = colonySize;
  }

  public Booking() {
    this.setBookingStatusAsBooked();
  }

  public Booking(
      String tenantPublicKey, String arrivalDate, Integer numberOfNights, String bedPackage) {

    this.setTenantPublicKey(tenantPublicKey);
    this.setArrivalDate(arrivalDate);
    this.setNumberOfNights(numberOfNights);
    this.setBedPackage(bedPackage);
    this.setBookingStatusAsBooked();
  }

  public void setTenantPublicKey(String tenantPublicKey) {
    this.tenantPublicKey = tenantPublicKey;
  }

  public String getTenantPublicKey() {
    return this.tenantPublicKey;
  }

  public void setArrivalDate(String arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public String getArrivalDate() {
    return this.arrivalDate;
  }

  public void setNumberOfNights(int numberOfNights) {
    this.numberOfNights = numberOfNights;
  }

  public int getNumberOfNights() {
    return this.numberOfNights;
  }

  public void setBedPackage(String bedPackage) {
    this.bedPackage = bedPackage;
  }

  public String getBedPackage() {
    return bedPackage;
  }

  public String calculateDepartureDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(this.arrivalDate);
    return localDate.plusDays(this.numberOfNights).format(formatter);
  }

  public String getBookingStatus() {
    return this.bookingStatus.getLabel();
  }

  public void setBookingStatusAsBooked() {
    this.bookingStatus = BookingStatus.BOOKED;
  }

  public void cancelBooking() {
    this.bookingStatus = BookingStatus.CANCELED;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public Integer getColonySize() {
    return this.colonySize;
  }
}
