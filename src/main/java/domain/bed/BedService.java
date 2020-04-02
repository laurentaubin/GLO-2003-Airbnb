package domain.bed;

import application.Query;
import application.bed.BedStarCalculator;
import application.bed.BedStarComparator;
import application.booking.BookingTotalPriceCalculator;
import application.booking.CancelationValidator;
import domain.bed.enums.PackageName;
import domain.booking.Booking;
import domain.transaction.TransactionService;
import infrastructure.bed.BedRepository;
import java.math.BigDecimal;
import java.util.*;
import presentation.bed.BedResponse;
import presentation.booking.BookingResponse;

public class BedService {
  private BedRepository bedRepository = new BedRepository();
  private static BedService bedService = null;
  private TransactionService transactionService = TransactionService.getInstance();

  private BedService() {};

  public static BedService getInstance() {
    if (bedService == null) {
      bedService = new BedService();
    }
    return bedService;
  }

  public String addBed(Bed bed) {
    String uuid = UUID.randomUUID().toString();
    bed.setUuid(uuid);
    bedRepository.addBed(uuid, bed);
    return uuid;
  }

  public int getTotalNumberOfBeds() {
    return bedRepository.getTotalNumberOfBeds();
  }

  public Bed getBedByUuid(String uuid) {
    return bedRepository.getBed(uuid);
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
            BedStarCalculator.calculateStars(bed),
            bed.getPackages(),
            bed.getLodgingMode());
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
    return bedRepository.getAllBeds();
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
    bedRepository.removeBed(uuid);
  }

  public void clearAllBeds() {
    bedRepository.clearAllBeds();
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
          && (Arrays.asList(query.getLodgingModes()).contains(bed.getLodgingMode()))
          && (bed.isAvailable(
              query.getArrivalDate(), query.getNumberOfNights(), query.getMinCapacity()))) {
        filteredBeds.add(bed);
      }
    }

    ArrayList<Bed> beds = sortBeds(filteredBeds);

    return beds;
  }

  public String addBooking(String bedUuid, Booking booking) {
    Bed bed = this.getBedByUuid(bedUuid);
    BigDecimal total = getBookingTotalPrice(bed, booking);
    booking.setTotal(total);
    return bed.addBooking(booking);
  }

  public BigDecimal getBookingTotalPrice(Bed bed, Booking booking) {
    BookingTotalPriceCalculator calculator =
        new BookingTotalPriceCalculator(bed.getPackages(), booking);
    return calculator.getTotalWithDiscount();
  }

  public void cancelBooking(String bedUuid, String bookingUuid) {
    Booking bookingToCancel = getBookingByUuid(bedUuid, bookingUuid);
    CancelationValidator validator = new CancelationValidator();
    validator.validateCancelation(bookingToCancel);
    bookingToCancel.cancelBooking();
    this.transactionService.addCancelationTransactions(bookingToCancel, getBedByUuid(bedUuid));
  }
}
