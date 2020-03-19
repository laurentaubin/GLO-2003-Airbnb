package bed.booking;

import bed.Bed;
import bed.BedPackage;
import bed.BedService;
import bed.booking.exception.BedAlreadyBookedException;
import bed.booking.exception.InvalidArrivalDateException;
import bed.booking.exception.InvalidBookingPackageException;
import bed.booking.exception.InvalidNumberOfNightsException;
import bed.booking.exception.InvalidTenantPublicKeyException;
import bed.booking.exception.PackageNotAvailableException;
import bed.booking.exception.UnallowedBookingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

  public void validateBooking(String bookingRequest, Bed bed)
      throws JsonProcessingException, ParseException {
    JsonNode bookingNode = mapper.readTree(bookingRequest);
    if (!isTenantPublicKeyPresent(bookingNode) || isTenantPublicKeyNull(bookingNode)) {
      throw new InvalidTenantPublicKeyException();
    } else {
      validateTenantPublicKey(bookingNode.get(TENANT_PUBLIC_KEY).textValue(), bed);
    }
    if (!isNumberOfNightsPresent(bookingNode) || isNumberOfNightsNull(bookingNode)) {
      throw new InvalidNumberOfNightsException();
    } else {
      validateNumberOfNights(bookingNode.get(NUMBER_OF_NIGHTS));
    }
    if (!isArrivalDatePresent(bookingNode) || isArrivalDateNull(bookingNode)) {
      throw new InvalidArrivalDateException();
    } else {
      validateArrivalDateFormat(bookingNode.get(ARRIVAL_DATE).textValue());
      validateIfThereIsConflictWithAnotherReservation(
          bookingNode.get(ARRIVAL_DATE).textValue(),
          bookingNode.get(NUMBER_OF_NIGHTS).asInt(),
          bed);
    }
    if (!isPackagePresent(bookingNode) || isPackageNull(bookingNode)) {
      throw new InvalidBookingPackageException();
    } else {
      validateBedPackage(bookingNode.get("package").textValue(), bed);
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

  public void validateTenantPublicKey(String tenantPublicKey, Bed bed) {
    validateTenantPublicKeyFormat(tenantPublicKey);
    validateTenantPublicKeyIsNotTheSameAsOwnerPublicKey(tenantPublicKey, bed);
  }

  private void validateTenantPublicKeyFormat(String tenantPublicKey) {
    if (!StringUtils.isAlphanumeric(tenantPublicKey) || tenantPublicKey.length() != 64) {
      throw new InvalidTenantPublicKeyException();
    }
  }

  private void validateTenantPublicKeyIsNotTheSameAsOwnerPublicKey(
      String tenantPublicKey, Bed bed) {
    String ownerPublicKey = bed.getOwnerPublicKey();
    if (tenantPublicKey.equals(ownerPublicKey)) {
      throw new UnallowedBookingException();
    }
  }

  public void validateArrivalDateFormat(String arrivalDate) throws ParseException {
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

  public void validateBedPackage(String askedPackage, Bed bed) {
    BedPackage[] offeredPackages = bed.getPackages();
    ArrayList<String> offeredPackagesName = new ArrayList<>();
    for (BedPackage offeredPackage : offeredPackages) {
      offeredPackagesName.add(offeredPackage.getName().toString());
    }
    if (!offeredPackagesName.contains(askedPackage)) {
      throw new PackageNotAvailableException();
    }
  }

  public void validateIfThereIsConflictWithAnotherReservation(
      String arrivalDate, int numberOfNightsAsked, Bed bed) throws ParseException {
    ArrayList<Booking> bookings = bed.getAllBookings();
    LocalDate askedArrivalDate = LocalDate.parse(arrivalDate);
    LocalDate askedDepartureDate = askedArrivalDate.plusDays(numberOfNightsAsked);
    for (Booking booking : bookings) {
      LocalDate bookingArrivalDate = LocalDate.parse(booking.getArrivalDate());
      LocalDate bookingDepartureDate = LocalDate.parse(booking.calculateDepartureDate());

      if (isAskedArrivalDateIncludedInDateRange(
              askedArrivalDate, bookingArrivalDate, bookingDepartureDate)
          || isAskedDepartureDateIncludedInDateRange(
              askedDepartureDate, bookingArrivalDate, bookingDepartureDate)) {
        throw new BedAlreadyBookedException();
      }

      if (isAskedArrivalDateOutsideDateRange(askedArrivalDate, bookingArrivalDate)
          && isAskedDepartureDateOutsideDateRange(askedDepartureDate, bookingDepartureDate)) {
        throw new BedAlreadyBookedException();
      }

      // La date d'arrivée et de départ sont les mêmes qu'une autre réservation
      if (isAskedArrivalDateTheSameAsOtherArrivalDate(askedArrivalDate, bookingArrivalDate)
          && isAskedDepartureTheSameAsOtherDepartureDate(
              askedDepartureDate, bookingDepartureDate)) {
        throw new BedAlreadyBookedException();
      }
    }
  }

  private boolean isAskedArrivalDateIncludedInDateRange(
      LocalDate askedArrivalTime, LocalDate bookingArrivaldate, LocalDate bookingDepartureDate) {
    return (askedArrivalTime.isAfter(bookingArrivaldate)
        && askedArrivalTime.isBefore(bookingDepartureDate));
  }

  private boolean isAskedDepartureDateIncludedInDateRange(
      LocalDate askedDepartureTime, LocalDate bookingArrivalDate, LocalDate bookingDepartureDate) {
    return (askedDepartureTime.isAfter(bookingArrivalDate)
        && askedDepartureTime.isBefore(bookingDepartureDate));
  }

  private boolean isAskedArrivalDateOutsideDateRange(
      LocalDate askedArrivalTime, LocalDate bookingArrivalDate) {
    return (askedArrivalTime.isBefore(bookingArrivalDate));
  }

  private boolean isAskedDepartureDateOutsideDateRange(
      LocalDate askedDepartureTime, LocalDate bookingDepartureDate) {
    return (askedDepartureTime.isAfter(bookingDepartureDate));
  }

  private boolean isAskedArrivalDateTheSameAsOtherArrivalDate(
      LocalDate askedArrivalDate, LocalDate bookingArrivalDate) {
    return askedArrivalDate.isEqual(bookingArrivalDate);
  }

  private boolean isAskedDepartureTheSameAsOtherDepartureDate(
      LocalDate askedDepartureDate, LocalDate bookingDepartudeDate) {
    return askedDepartureDate.isEqual(bookingDepartudeDate);
  }
}
