package bed;

import exceptions.bed.InvalidAllYouCanDrinkException;
import exceptions.bed.InvalidCapacityException;
import exceptions.bed.InvalidOwnerKeyException;
import exceptions.bed.InvalidSweetToothPackageException;
import exceptions.bed.InvalidZipCodeException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public class BedValidator {
  public BedValidator() {}

  public void validateBed(Bed bed) {
    if (!isPublicKeyValid(bed.fetchOwnerPublicKey())) {
      throw new InvalidOwnerKeyException();
    } else if (!isZipCodeValid(bed.getZipCode())) {
      throw new InvalidZipCodeException();
    } else if (!isCapacityValid(bed.getCapacity())) {
      throw new InvalidCapacityException();
    }
    validateBedPackage(bed.getPackages());
  }

  public boolean isPublicKeyValid(String publicKey) {
    return StringUtils.isAlphanumeric(publicKey) && publicKey.length() == 64;
  }

  public boolean isZipCodeValid(String postalCode) {
    return postalCode.length() == 5 && StringUtils.isNumeric(postalCode);
  }

  public boolean isCapacityValid(int capacity) {
    return capacity > 0;
  }

  public void validateBedPackage(BedPackage[] givenBedPackages) {
    ArrayList<String> packageNameArrayList = new ArrayList<>();
    for (BedPackage bedPackage : givenBedPackages) {
      packageNameArrayList.add(bedPackage.getName().toString());
    }
    if (packageNameArrayList.contains(PackageName.SWEET_TOOTH.toString())) {
      if (!packageNameArrayList.contains(PackageName.BLOOD_THIRSTY.toString())
          || !packageNameArrayList.contains(PackageName.ALL_YOU_CAN_DRINK.toString())) {
        throw new InvalidSweetToothPackageException();
      }
    } else if (packageNameArrayList.contains(PackageName.ALL_YOU_CAN_DRINK.toString())) {
      if (!packageNameArrayList.contains(PackageName.BLOOD_THIRSTY.toString())) {
        throw new InvalidAllYouCanDrinkException();
      }
    }
  }
}
