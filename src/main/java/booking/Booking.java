package booking;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
  private String tenantPublicKey;
  private String arrivalDate;
  private Integer numberOfNights;
  private String bedPackage;

  public Booking() {}

  public Booking(
      String tenantPublicKey, String arrivalDate, Integer numberOfNights, String bedPackage) {

    this.setTenantPublicKey(tenantPublicKey);
    this.setArrivalDate(arrivalDate);
    this.setNumberOfNights(numberOfNights);
    this.setBedPackage(bedPackage);
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

  public String calculateDepartureDate() throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(this.arrivalDate);
    return localDate.plusDays(this.numberOfNights).format(formatter);
  }
}
