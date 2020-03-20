package bed;

import bed.booking.Booking;
import bed.booking.BookingResponse;
import bed.booking.exception.BedNotFoundException;
import java.util.*;

public class BedService {
  private Map<String, Bed> beds = new HashMap<String, Bed>();
  private static BedService bedService = null;

  private BedService() {};

  public static BedService getInstance() {
    if (bedService == null) {
      bedService = new BedService();
    }
    return bedService;
  }

  public String addBed(Bed bed, String uuid) {
    beds.put(uuid, bed);
    return uuid;
  }

  public int getTotalNumberOfBeds() {
    return beds.size();
  }

  public Bed getBedByUuid(String uuid) {
    if (!beds.containsKey(uuid)) {
      throw new BedNotFoundException(uuid);
    }
    return beds.get(uuid);
  }

  public BedResponse getBedResponseByUuid(String uuid) {
    Bed bed = this.getBedByUuid(uuid);
    BedResponse bedResponse =
        new BedResponse(
            bed.getUuid(),
            bed.getZipCode(),
            bed.getBedType(),
            bed.getCleaningFrequency(),
            bed.getBloodTypes(),
            bed.getCapacity(),
            bed.getStars(),
            bed.getPackages());
    return bedResponse;
  }

  public Booking getBookingByUuid(String bedUuid, String bookingUuid) {
    Bed bed = this.getBedByUuid(bedUuid);
    return bed.getBookingByUuid(bookingUuid);
  }

  public BookingResponse getBookingResponseByUuid(String bedUuid, String bookingUuid) {
    Bed bed = this.getBedByUuid(bedUuid);
    Booking booking = bed.getBookingByUuid(bookingUuid);
    BookingResponse bookingResponse = new BookingResponse(booking);
    return bookingResponse;
  }

  public ArrayList<Bed> getAllBeds() {
    return new ArrayList<Bed>(beds.values());
  }

  public ArrayList<BookingResponse> getAllBookingsForBed(String bedUuid) {
    Bed bed = this.getBedByUuid(bedUuid);
    ArrayList<BookingResponse> bookings = new ArrayList<>();
    for (Booking booking : bed.getAllBookings()) {
      BookingResponse bookingResponse = new BookingResponse(booking);
      bookings.add(bookingResponse);
    }
    return bookings;
  }

  public void removeBed(String uuid) {
    this.beds.remove(uuid);
  }

  public void clearAllBeds() {
    beds.clear();
  }

  private ArrayList<Bed> sortBeds(ArrayList<Bed> beds) {
    Collections.sort(beds, new BedStarComparator());
    Collections.reverse(beds);
    return beds;
  }

  public ArrayList<Bed> Get(Query query) {
    ArrayList<Bed> filteredBeds = new ArrayList<>();
    for (Bed bed : getAllBeds()) {
      Set<PackageName> bedPackagesNamesSet = new HashSet<>(Arrays.asList(bed.packagesNames()));
      Set<PackageName> queryPackagesNamesSet =
          new HashSet<>(Arrays.asList(query.getPackagesNames()));
      if ((!Collections.disjoint(bedPackagesNamesSet, queryPackagesNamesSet))
          && (Arrays.asList(bed.getBloodTypes()).containsAll(Arrays.asList(query.getBloodTypes())))
          && (Arrays.asList(query.getCleaningFrequencies()).contains(bed.getCleaningFrequency()))
          && (Arrays.asList(query.getBedTypes()).contains(bed.getBedType()))
          && (bed.getCapacity() >= query.getMinCapacity())) {
        filteredBeds.add(bed);
      }
    }

    ArrayList<Bed> beds = sortBeds(filteredBeds);

    return beds;
  }

  public void cancelBooking(String bedUuid, String bookingUuid) {
    Booking bookingToCancel = getBookingByUuid(bedUuid, bookingUuid);
    CancelationValidator validator = new CancelationValidator();
    validator.validateCancelation(bookingToCancel);
    bookingToCancel.cancelBooking();
  }
}
