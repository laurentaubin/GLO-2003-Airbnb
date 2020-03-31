package presentation.bed;

import domain.bed.enums.BedPackage;

public class BedPackageResponse {
  private String name;
  private double price;

  public BedPackageResponse(BedPackage bedPackage) {
    this.name = bedPackage.getName().toString();
    this.price = bedPackage.getPricePerNight();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
