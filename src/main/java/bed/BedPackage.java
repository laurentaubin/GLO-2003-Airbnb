package bed;

public class BedPackage {
  private PackageName name;
  private double pricePerNight;

  BedPackage() {};

  public BedPackage(PackageName name, double pricePerNight) {
    this.setName(name);
    this.setPricePerNight(pricePerNight);
  }

  public PackageName getName() {
    return name;
  }

  public void setName(PackageName name) {
    this.name = name;
  }

  public double getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(double pricePerNight) {
    this.pricePerNight = pricePerNight;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    BedPackage other = (BedPackage) obj;
    return this.getName().equals(other.getName())
        && this.getPricePerNight() == other.getPricePerNight();
  }
}
