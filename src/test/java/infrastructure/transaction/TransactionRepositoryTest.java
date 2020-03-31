package infrastructure.transaction;

import static org.junit.jupiter.api.Assertions.*;

import domain.transaction.Transaction;
import domain.transaction.TransactionReason;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionRepositoryTest {
  private TransactionRepository transactionRepository;

  @BeforeEach
  void setup() {
    this.transactionRepository = new TransactionRepository();
  }

  @Test
  void getTransactions_whenNoTransactionAdded_shouldReturnEmptyRepo() {
    assertEquals(0, transactionRepository.getTransactions().size());
  }

  @Test
  void addTransaction_whenOneTransactionAdded_shouldAddOneTransactionToRepo() {
    Transaction transaction =
        new Transaction(
            LocalDateTime.now().atZone(ZoneOffset.UTC).toString(),
            "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E",
            "airbnb",
            20.30,
            TransactionReason.STAY_BOOKED.toString());
    this.transactionRepository.addTransaction(transaction);
    assertEquals(1, transactionRepository.getTransactions().size());
  }
}
