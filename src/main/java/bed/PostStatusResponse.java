package bed;

public enum PostStatusResponse {
  INVALID_PUBLIC_KEY("INVALID_PUBLIC_KEY"),
  INVALID_ZIP_CODE("INVALID_ZIP_CODE"),
  INVALID_BED_TYPE("INVALID_BED_TYPE"),
  INVALID_CLEANING_FREQUENCY("INVALID_CLEANING_FREQUENCY"),
  INVALID_BLOOD_TYPE("INVALID_BLOOD_TYPES"),
  INVALID_PACKAGES("INVALID_PACKAGES"),
  EXCEEDING_ACCOMMODATION_CAPACITY("EXCEEDING_ACCOMMODATION_CAPACITY"),
  CANT_OFFER_ALL_YOU_CAN_DRINK_PACKAGE("CANT_OFFER_ALL_YOU_CAN_DRINK_PACKAGE"),
  CANT_OFFER_SWEET_TOOTH_PACKAGE("CANT_OFFER_SWEET_TOOTH_PACKAGE");

  private String label;

  PostStatusResponse(String label) {
    this.label = label;
  }
}
