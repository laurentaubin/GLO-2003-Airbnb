package bed;

import exceptions.bed.InvalidCapacityException;
import exceptions.bed.InvalidOwnerKeyException;
import exceptions.bed.InvalidZipCodeException;
import org.apache.commons.lang3.StringUtils;

public class BedValidator {
  public BedValidator() {}

  public void validateBed(Bed bed) {
    if (!isPublicKeyValid(bed.getOwnerPublicKey())) {
      throw new InvalidOwnerKeyException();
    } else if (!isZipCodeValid(bed.getZipCode())) {
      throw new InvalidZipCodeException();
    } else if (!isCapacityValid(bed.getCapacity())) {
      throw new InvalidCapacityException();
    }
  }

  private boolean isPublicKeyValid(String publicKey) {
    return StringUtils.isAlphanumeric(publicKey) && publicKey.length() == 64;
  }

  private boolean isZipCodeValid(String postalCode) {
    return postalCode.length() == 5 && StringUtils.isNumeric(postalCode);
  }

  private boolean isCapacityValid(int capacity) {
    return capacity > 0;
  }
}
