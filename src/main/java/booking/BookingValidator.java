package booking;

import bed.BedService;
import exceptions.booking.Booking.UnallowedBookingException;
import exceptions.booking.BookingService.InvalidTenantPublicKeyException;
import exceptions.booking.NumberOfNights.InvalidNumberOfNightsException;
import org.apache.commons.lang3.StringUtils;

public class BookingValidator {
  private BedService bedService = BedService.getInstance();

  public BookingValidator() {}

  public void validateBooking(Booking booking, String bedNumber) {
    if (!isTenantKeyValid(booking.getTenantPublicKey())) {
      throw new InvalidTenantPublicKeyException();
    } else if (isTenantKeySameAsOwnerKey(booking.getTenantPublicKey(), bedNumber)) {
      throw new UnallowedBookingException();
    } else if (!isNumberOfNightsValid(booking.getNumberOfNights())) {
      throw new InvalidNumberOfNightsException();
    }
  }

  private boolean isTenantKeyValid(String publicKey) {
    return StringUtils.isAlphanumeric(publicKey) && publicKey.length() == 64;
  }

  private boolean isTenantKeySameAsOwnerKey(String tenantKey, String bedNumber) {
    String ownerKey = bedService.getBedByUuid(bedNumber).getOwnerPublicKey();
    return tenantKey.equals(ownerKey);
  }

  private boolean isNumberOfNightsValid(Integer numberOfNights) {
    return numberOfNights >= 1 && numberOfNights <= 90;
  }
}
