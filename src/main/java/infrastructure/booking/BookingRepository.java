package infrastructure.booking;

import domain.booking.Booking;
import domain.booking.exception.BookingNotFoundException;
import java.util.*;

public class BookingRepository {
  private Map<String, Booking> bookings = new HashMap<>();

  public BookingRepository() {};

  public void addBooking(String uuid, Booking booking) {
    this.bookings.put(uuid, booking);
  }

  public boolean containsBooking(String uuid) {
    return bookings.containsKey(uuid);
  }

  public Booking getBooking(String uuid) {
    if (!containsBooking(uuid)) {
      throw new BookingNotFoundException(uuid);
    }
    return bookings.get(uuid);
  }

  public ArrayList<Booking> getAllBookings() {
    return new ArrayList<Booking>(bookings.values());
  }
}
