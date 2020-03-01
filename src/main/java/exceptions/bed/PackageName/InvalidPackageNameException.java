package exceptions.bed.PackageName;

import exceptions.BedException;

public class InvalidPackageNameException extends BedException {
  public InvalidPackageNameException() {
    super(
        "INVALID_PACKAGES",
        "packages should be an array of package name with " + "its corresponding price per night");
  }

  public InvalidPackageNameException(String s) {
    super(s);
  }
}
