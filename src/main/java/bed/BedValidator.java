package bed;

import bed.exception.ExceedingAccommodationCapacityException;
import bed.exception.InvalidAllYouCanDrinkException;
import bed.exception.InvalidBedTypeException;
import bed.exception.InvalidBloodTypeException;
import bed.exception.InvalidCapacityException;
import bed.exception.InvalidCleaningFrequencyException;
import bed.exception.InvalidOwnerKeyException;
import bed.exception.InvalidPackageNameException;
import bed.exception.InvalidSweetToothPackageException;
import bed.exception.InvalidZipCodeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class BedValidator {
  private ObjectMapper mapper = new ObjectMapper();
  private final String OWNER_PUBLIC_KEY = "ownerPublicKey";
  private final String ZIP_CODE = "zipCode";
  private final String BED_TYPE = "bedType";
  private final String CLEANING_FREQUENCY = "cleaningFrequency";
  private final String BLOOD_TYPES = "bloodTypes";
  private final String CAPACITY = "capacity";
  private final String PACKAGES = "packages";
  private final String NAME = "name";
  private final String PRICE_PER_NIGHT = "pricePerNight";

  public BedValidator() {}

  public void validateBed(String jsonRequestBody) throws JsonProcessingException {
    JsonNode bedNode = mapper.readTree(jsonRequestBody);
    if (!bedNode.has(OWNER_PUBLIC_KEY) || bedNode.get(OWNER_PUBLIC_KEY).isNull()) {
      throw new InvalidOwnerKeyException();
    } else {
      validateOwnerPublicKey(bedNode.get(OWNER_PUBLIC_KEY).textValue());
    }
    if (!bedNode.has(ZIP_CODE) || bedNode.get(ZIP_CODE).isNull()) {
      throw new InvalidZipCodeException();
    } else {
      validateZipCode(bedNode.get(ZIP_CODE).textValue());
    }
    if (!bedNode.has(BED_TYPE) || bedNode.get(BED_TYPE).isNull()) {
      throw new InvalidBedTypeException();
    } else {
      validateBedType(bedNode.get(BED_TYPE).textValue());
    }
    if (!bedNode.has(CLEANING_FREQUENCY) || bedNode.get(CLEANING_FREQUENCY).isNull()) {
      throw new InvalidCleaningFrequencyException();
    } else {
      validateCleaningFrequency(bedNode.get(CLEANING_FREQUENCY).textValue());
    }
    if (!bedNode.has(BLOOD_TYPES) || bedNode.get(BLOOD_TYPES).isNull()) {
      throw new InvalidBloodTypeException();
    } else {
      validateBloodTypes(bedNode.get(BLOOD_TYPES));
    }
    if (!bedNode.has(CAPACITY) || bedNode.get(CAPACITY).isNull()) {
      throw new InvalidCapacityException();
    } else {
      validateCapacity(bedNode.get(CAPACITY), bedNode.get(BED_TYPE).textValue());
    }
    if (!bedNode.has(PACKAGES) || bedNode.get(PACKAGES).isNull()) {
      throw new InvalidPackageNameException();
    } else {
      validateBedPackages(bedNode.get(PACKAGES));
    }
  }

  public void validateOwnerPublicKey(String ownerPublicKey) {
    if (!StringUtils.isAlphanumeric(ownerPublicKey) || ownerPublicKey.length() != 64) {
      throw new InvalidOwnerKeyException();
    }
  }

  public void validateZipCode(String zipCode) {
    if (zipCode.length() != 5 || !StringUtils.isNumeric(zipCode)) {
      throw new InvalidZipCodeException();
    }
  }

  public void validateBedType(String bedType) {
    BedType.valueOfLabel(bedType);
  }

  public void validateCleaningFrequency(String cleaningFrequency) {
    CleaningFrequency.valueOfLabel(cleaningFrequency);
  }

  public void validateBloodTypes(JsonNode bloodTypesNode) {
    if (!bloodTypesNode.isArray() || bloodTypesNode.size() == 0) {
      throw new InvalidBloodTypeException();
    }
    for (int i = 0; i < bloodTypesNode.size(); i++) {
      BloodType.valueOfLabel(bloodTypesNode.get(i).textValue());
    }
  }

  public void validateCapacity(JsonNode capacityNode, String bedType) {
    if (!capacityNode.isInt()) {
      throw new InvalidCapacityException();
    } else {
      int capacity = capacityNode.asInt();
      if (capacity <= 0) {
        throw new InvalidCapacityException();
      } else {
        if (isCapacityExceedingMaximumAccommodation(capacity, bedType)) {
          throw new ExceedingAccommodationCapacityException();
        }
      }
    }
  }

  private boolean isCapacityExceedingMaximumAccommodation(int capacity, String bedType) {
    if (bedType.equals(BedType.LATEX.toString()) && capacity > BedType.LATEX.getMaxCapacity()) {
      return true;
    } else if (bedType.equals(BedType.MEMORY_FOAM.toString())
        && capacity > BedType.MEMORY_FOAM.getMaxCapacity()) {
      return true;
    } else if (bedType.equals(BedType.SPRINGS.toString())
        && capacity > BedType.SPRINGS.getMaxCapacity()) {
      return true;
    }
    return false;
  }

  public void validateBedPackages(JsonNode packagesNode) {

    if (!packagesNode.isArray() || packagesNode.size() == 0) {
      throw new InvalidPackageNameException();
    } else {
      ArrayList<String> packageNameArrayList = new ArrayList<>();
      for (int i = 0; i < packagesNode.size(); i++) {
        if (!packagesNode.get(i).has(NAME)
            || !packagesNode.get(i).has(PRICE_PER_NIGHT)
            || !packagesNode.get(i).get(PRICE_PER_NIGHT).isNumber()
            || packagesNode.get(i).get(PRICE_PER_NIGHT).asDouble() <= 0
            || doesPricePerNightHaveMoreThanTwoDecimals(
                packagesNode.get(i).get(PRICE_PER_NIGHT).toString())) {
          throw new InvalidPackageNameException();
        }
        PackageName.valueOfLabel(packagesNode.get(i).get("name").textValue());
        packageNameArrayList.add(packagesNode.get(i).get("name").textValue());
      }
      validatePresenceOfNecessaryPackage(packageNameArrayList);
      if (isPackagePresentMoreThanOnce(packageNameArrayList)) {
        throw new InvalidPackageNameException();
      }
    }
  }

  private boolean doesPricePerNightHaveMoreThanTwoDecimals(String pricePerNightAsString) {
    int dotIndex = pricePerNightAsString.indexOf(".");
    if (dotIndex > 0) {
      String pricePerNightCents = pricePerNightAsString.substring(dotIndex + 1);
      return pricePerNightCents.length() > 2;
    }
    return false;
  }

  private boolean isPackagePresentMoreThanOnce(ArrayList<String> packageNameArrayList) {
    Set<String> packageNameSet = new HashSet<String>(packageNameArrayList);
    for (String packageName : packageNameSet) {
      if (Collections.frequency(packageNameArrayList, packageName) > 1) {
        return true;
      }
    }
    return false;
  }

  private void validatePresenceOfNecessaryPackage(ArrayList<String> packageNameArrayList) {
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
