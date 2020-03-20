package bed;

import bed.booking.Booking;
import bed.booking.BookingStatus;
import bed.booking.exception.BookingAlreadyCanceledException;
import bed.booking.exception.CancelationNotAllowedException;
import java.time.LocalDate;

public class CancelationValidator {
  private LocalDate cancelationDate;

  public CancelationValidator() {
    this.cancelationDate = LocalDate.now();
  }

  public void setCancelationDate(LocalDate cancelationDate) {
    this.cancelationDate = cancelationDate;
  }

  public void validateCancelation(Booking bookingToCancel) {
    validateThatCancelationDateAtLeastOneDayBeforeArrivalDate(bookingToCancel);
    validateThatBookingIsNotAlreadyCanceled(bookingToCancel);
  }

  public void validateThatCancelationDateAtLeastOneDayBeforeArrivalDate(Booking bookingToCancel) {
    if (!isCancelationDateAtLeastOneDayBeforeArrivalDate(bookingToCancel)) {
      throw new CancelationNotAllowedException();
    }
  }

  public void validateThatBookingIsNotAlreadyCanceled(Booking bookingToCancel) {
    if (isBookingAlreadyCanceled(bookingToCancel)) {
      throw new BookingAlreadyCanceledException();
    }
  }

  public boolean isCancelationDateAtLeastOneDayBeforeArrivalDate(Booking bookingToCancel) {
    LocalDate limitCancelationDate = LocalDate.parse(bookingToCancel.getArrivalDate()).minusDays(2);
    if (cancelationDate.isAfter(limitCancelationDate)) {
      return false;
    }
    return true;
  }

  public boolean isBookingAlreadyCanceled(Booking bookingToCancel) {
    if (bookingToCancel.getBookingStatus().equals(BookingStatus.CANCELED.getLabel())) {
      return true;
    }
    return false;
  }
}
