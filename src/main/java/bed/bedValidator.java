package bed;

import org.apache.commons.lang3.StringUtils;

public class bedValidator {
  static boolean isPublicKeyValid(String publicKey) {
    return StringUtils.isAlphanumeric(publicKey) && publicKey.length() == 25;
  }

  static boolean isPostalCodeValid(String postalCode) {
    return postalCode.length() == 5 && StringUtils.isNumeric(postalCode);
  }

  static boolean isCapacityValid(int capacity) {
    return capacity > 0;
  }
}
