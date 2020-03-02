package exceptions.Transactions;

import exceptions.TransactionException;

public class InvalidTimestampException extends TransactionException {
  public InvalidTimestampException() {
    super("INVALID_TIMESTAMP", "The transaction's timestamp is not valid");
  }
}
