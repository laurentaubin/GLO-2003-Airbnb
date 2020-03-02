package booking;

import exceptions.booking.BookingService.InvalidUuidException;
import java.util.*;

public class BookingService {
  private Map<String, Booking> bookings = new HashMap<String, Booking>();

  public BookingService() {};

  public String addBooking(Booking booking) {
    String bookingUuid = UUID.randomUUID().toString();
    bookings.put(bookingUuid, booking);
    return bookingUuid;
  }

  public int getTotalNumberOfBookings() {
    return bookings.size();
  }

  public Booking getBookingByUuid(String uuid) throws InvalidUuidException {
    if (!bookings.containsKey(uuid)) {
      throw new InvalidUuidException();
    }
    return bookings.get(uuid);
  }

  public List<Booking> getAllBookings() {
    return new ArrayList<Booking>(bookings.values());
  }
}
