package transactions;

import bed.Bed;
import bed.booking.Booking;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class TransactionService {
  private TransactionRepository transactionRepository = new TransactionRepository();
  private final String AIRBNB = "airbnb";
  private static TransactionService transactionService;

  private TransactionService() {}

  public static TransactionService getInstance() {
    if (transactionService == null) {
      transactionService = new TransactionService();
    }
    return transactionService;
  }

  public ArrayList<Transaction> getTransactions() {
    return transactionRepository.getTransactions();
  }

  public void addBookedTransactions(Booking booking, Bed bed) {
    Transaction tenantTransaction = this.getTenantBookedTransaction(booking);
    Transaction ownerTransaction = this.getOwnerBookedTransaction(bed, booking);
    transactionRepository.addTransaction(tenantTransaction);
    transactionRepository.addTransaction(ownerTransaction);
  }

  public void addCancelationTransactions(Booking booking, Bed bed) {
    if (isCancelationEligibleForTotalRefund(booking)) {
      this.addCanceledBookingWithTotalRefundTransactions(booking, bed);
    } else if (isCancelationEligibleForPartialRefund(booking)) {
      this.addCanceledBookingWithPartialRefundTransactions(booking, bed);
    }
  }

  public boolean isCancelationEligibleForTotalRefund(Booking booking) {
    LocalDate limitTotalRefundCancelationDate =
        LocalDate.parse(booking.getArrivalDate()).minusDays(8);
    return LocalDate.now().isBefore(limitTotalRefundCancelationDate)
        || LocalDate.now().isEqual((limitTotalRefundCancelationDate));
  }

  public boolean isCancelationEligibleForPartialRefund(Booking booking) {
    LocalDate limitPartialRefundCancelationDate =
        LocalDate.parse(booking.getArrivalDate()).minusDays(2);
    return LocalDate.now().isBefore(limitPartialRefundCancelationDate)
        || LocalDate.now().isEqual(limitPartialRefundCancelationDate);
  }

  private Transaction getTenantBookedTransaction(Booking booking) {
    return new Transaction(
        LocalDateTime.now().atZone(ZoneOffset.UTC).toString(),
        booking.getTenantPublicKey(),
        AIRBNB,
        booking.getTotal(),
        TransactionReason.STAY_BOOKED.toString());
  }

  private Transaction getOwnerBookedTransaction(Bed bed, Booking booking) {
    return new Transaction(
        this.getEndOfBookingTimestamp(booking),
        AIRBNB,
        bed.getOwnerPublicKey(),
        booking.getTotal(),
        TransactionReason.STAY_COMPLETED.toString());
  }

  public String getEndOfBookingTimestamp(Booking booking) {
    LocalDate arrivalDate = LocalDate.parse(booking.getArrivalDate());
    int numberOfNights = booking.getNumberOfNights();
    LocalDate lastDayOfBooking = arrivalDate.plusDays(numberOfNights);
    return lastDayOfBooking.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toString();
  }

  private void addCanceledBookingWithTotalRefundTransactions(Booking booking, Bed bed) {
    Transaction tenantTransaction = this.getTenantCanceledTransactionWithTotalRefund(booking);
    Transaction ownerTransaction = this.getOwnerCanceledTransactionWithTotalRefund(bed, booking);
    transactionRepository.addTransaction(tenantTransaction);
    transactionRepository.addTransaction(ownerTransaction);
  }

  private Transaction getTenantCanceledTransactionWithTotalRefund(Booking booking) {
    return new Transaction(
        LocalDateTime.now().atZone(ZoneOffset.UTC).toString(),
        AIRBNB,
        booking.getTenantPublicKey(),
        booking.getTotal(),
        TransactionReason.STAY_CANCELED.toString());
  }

  private Transaction getOwnerCanceledTransactionWithTotalRefund(Bed bed, Booking booking) {
    return new Transaction(
        this.getEndOfBookingTimestamp(booking),
        bed.getOwnerPublicKey(),
        AIRBNB,
        booking.getTotal(),
        TransactionReason.STAY_CANCELED.toString());
  }

  private void addCanceledBookingWithPartialRefundTransactions(Booking booking, Bed bed) {
    Transaction tenantTransaction = this.getTenantCanceledTransactionWithPartialRefund(booking);
    Transaction ownerTransaction = this.getOwnerCanceledTransactionWithPartialPayment(bed, booking);
    Transaction airbnbTransaction = this.getAirbnbCanceledTransaction(bed, booking);
    transactionRepository.addTransaction(tenantTransaction);
    transactionRepository.addTransaction(ownerTransaction);
    transactionRepository.addTransaction(airbnbTransaction);
  }

  private Transaction getTenantCanceledTransactionWithPartialRefund(Booking booking) {
    return new Transaction(
        LocalDateTime.now().atZone(ZoneOffset.UTC).toString(),
        AIRBNB,
        booking.getTenantPublicKey(),
        this.getTenantPartialRefundAmount(booking.getTotal()),
        TransactionReason.STAY_CANCELED.toString());
  }

  private Number getTenantPartialRefundAmount(BigDecimal total) {
    return total.divide(BigDecimal.valueOf(2), RoundingMode.HALF_DOWN);
  }

  private Transaction getOwnerCanceledTransactionWithPartialPayment(Bed bed, Booking booking) {
    return new Transaction(
        LocalDateTime.now().atZone(ZoneOffset.UTC).toString(),
        AIRBNB,
        bed.getOwnerPublicKey(),
        this.getOwnerPartialPaymentAmount(booking.getTotal()),
        TransactionReason.STAY_CANCELED.toString());
  }

  private Number getOwnerPartialPaymentAmount(BigDecimal total) {
    return total.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
  }

  private Transaction getAirbnbCanceledTransaction(Bed bed, Booking booking) {
    return new Transaction(
        this.getEndOfBookingTimestamp(booking),
        bed.getOwnerPublicKey(),
        AIRBNB,
        booking.getTotal(),
        TransactionReason.STAY_CANCELED.toString());
  }
}
