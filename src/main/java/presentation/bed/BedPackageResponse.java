package presentation.bed;

import domain.bed.enums.BedPackage;

public class BedPackageResponse {
  private String name;
  private double pricePerNight;

  public BedPackageResponse(BedPackage bedPackage) {
    this.name = bedPackage.getName().toString();
    this.pricePerNight = bedPackage.getPricePerNight();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPricePerNight() {
    return this.pricePerNight;
  }

  public void setPricePerNight(double price) {
    this.pricePerNight = price;
  }
}
