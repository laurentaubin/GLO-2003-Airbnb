package booking;

import exceptions.booking.BookingService.InvalidUuidException;
import java.util.*;

public class BookingService {
  private Map<String, Booking> bookings = new HashMap<String, Booking>();
  private static BookingService bookingService = null;

  private BookingService() {};

  public static BookingService getInstance() {
    if (bookingService == null) {
      bookingService = new BookingService();
    }
    return bookingService;
  }

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

  public void clearAll() {
    bookings.clear();
  }
}
