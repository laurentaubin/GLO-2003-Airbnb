package exceptions.bed.PackageName;

import exceptions.bed.BedEnumException;

public class InvalidPackageNameException extends BedEnumException {
  public InvalidPackageNameException() {
    super();
  }

  public InvalidPackageNameException(String s) {
    super(s);
  }
}
