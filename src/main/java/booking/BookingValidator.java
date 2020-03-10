package booking;

import bed.BedPackage;
import bed.BedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.booking.ArrivalDate.InvalidArrivalDateException;
import exceptions.booking.BedPackage.InvalidBookingPackageException;
import exceptions.booking.BedPackage.PackageNotAvailableException;
import exceptions.booking.BookingService.InvalidTenantPublicKeyException;
import exceptions.booking.NumberOfNights.InvalidNumberOfNightsException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

public class BookingValidator {
  private BedService bedService = BedService.getInstance();
  private ObjectMapper mapper = new ObjectMapper();
  private String TENANT_PUBLIC_KEY = "tenantPublicKey";
  private String ARRIVAL_DATE = "arrivalDate";
  private String NUMBER_OF_NIGHTS = "numberOfNights";
  private String PACKAGE = "package";

  public BookingValidator() {}

  public void validateBooking(String bookingRequest, String bedNumber)
      throws JsonProcessingException, ParseException {
    JsonNode bookingNode = mapper.readTree(bookingRequest);
    if (!isTenantPublicKeyPresent(bookingNode) || isTenantPublicKeyNull(bookingNode)) {
      throw new InvalidTenantPublicKeyException();
    } else {
      validateTenantPublicKey(bookingNode.get(TENANT_PUBLIC_KEY).textValue(), bedNumber);
    }
    if (!isArrivalDatePresent(bookingNode) || isArrivalDateNull(bookingNode)) {
      throw new InvalidArrivalDateException();
    } else {
      validateArrivalDate(bookingNode.get(ARRIVAL_DATE).textValue());
    }
    if (!isNumberOfNightsPresent(bookingNode) || isNumberOfNightsNull(bookingNode)) {
      throw new InvalidNumberOfNightsException();
    } else {
      validateNumberOfNights(bookingNode.get(NUMBER_OF_NIGHTS));
    }
    if (!isPackagePresent(bookingNode) || isPackageNull(bookingNode)) {
      throw new InvalidBookingPackageException();
    } else {
      validateBedPackage(bookingNode.get("package").textValue(), bedNumber);
    }
  }

  public boolean isTenantPublicKeyPresent(JsonNode bookingNode) {
    return bookingNode.has(TENANT_PUBLIC_KEY);
  }

  public boolean isTenantPublicKeyNull(JsonNode bookingNode) {
    return bookingNode.get(TENANT_PUBLIC_KEY).isNull();
  }

  public boolean isArrivalDatePresent(JsonNode bookingNode) {
    return bookingNode.has(ARRIVAL_DATE);
  }

  public boolean isArrivalDateNull(JsonNode bookingNode) {
    return bookingNode.get(ARRIVAL_DATE).isNull();
  }

  public boolean isNumberOfNightsPresent(JsonNode bookingNode) {
    return bookingNode.has(NUMBER_OF_NIGHTS);
  }

  public boolean isNumberOfNightsNull(JsonNode bookingNode) {
    return bookingNode.get(NUMBER_OF_NIGHTS).isNull();
  }

  public boolean isPackagePresent(JsonNode bookingNode) {
    return bookingNode.has(PACKAGE);
  }

  public boolean isPackageNull(JsonNode bookingNode) {
    return bookingNode.get(PACKAGE).isNull();
  }

  public void validateTenantPublicKey(String tenantPublicKey, String bedNumber) {
    String ownerPublicKey = bedService.getBedByUuid(bedNumber).fetchOwnerPublicKey();
    if (!StringUtils.isAlphanumeric(tenantPublicKey)
        || tenantPublicKey.length() != 64
        || tenantPublicKey.equals(ownerPublicKey)) {
      throw new InvalidTenantPublicKeyException();
    }
  }

  public void validateArrivalDate(String arrivalDate) throws ParseException {
    if (!GenericValidator.isDate(arrivalDate, "yyyy-MM-dd", true)) {
      throw new InvalidArrivalDateException();
    } else {
      if (new SimpleDateFormat("yyyy-MM-dd").parse(arrivalDate).before(new Date())) {
        throw new InvalidArrivalDateException();
      }
    }
  }

  public void validateNumberOfNights(JsonNode numberOfNightsNode) {
    if (!numberOfNightsNode.isInt()) {
      throw new InvalidNumberOfNightsException();
    } else {
      int numberOfNights = numberOfNightsNode.asInt();
      if (numberOfNights < 1 || numberOfNights > 90) {
        throw new InvalidNumberOfNightsException();
      }
    }
  }

  public void validateBedPackage(String askedPackage, String uuid) {
    BedPackage[] offeredPackages = this.bedService.getBedByUuid(uuid).getPackages();
    ArrayList<String> offeredPackagesName = new ArrayList<>();
    for (BedPackage offeredPackage : offeredPackages) {
      offeredPackagesName.add(offeredPackage.getName().toString());
    }
    if (!offeredPackagesName.contains(askedPackage)) {
      throw new PackageNotAvailableException();
    }
  }
}
