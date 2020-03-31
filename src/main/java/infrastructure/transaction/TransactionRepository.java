package infrastructure.transaction;

import domain.transaction.Transaction;
import java.util.ArrayList;

public class TransactionRepository {
  private static ArrayList<Transaction> transactions;

  public TransactionRepository() {
    transactions = new ArrayList<>();
  }

  public ArrayList<Transaction> getTransactions() {
    return new ArrayList<>(transactions);
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }
}
