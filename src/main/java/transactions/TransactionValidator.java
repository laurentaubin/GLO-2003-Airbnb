package transactions;

import java.math.BigDecimal;
import transactions.exception.InvalidTimestampException;

public class TransactionValidator {
  public TransactionValidator() {}

  public static void validateTransaction(Transaction transaction) {
    if (!isTimestampValid(transaction.getTimestamp())) {
      throw new InvalidTimestampException();
    }
  }

  public static boolean isTimestampValid(String timestamp) {
    // yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
    String[] splittedTimestamp = timestamp.split("T", 2);
    String date_and_year = splittedTimestamp[0];
    String hours_and_minutes = splittedTimestamp[1];

    // check year
    if (Integer.parseInt(date_and_year.substring(0, 3)) > 2020) {
      return false;
    }
    // check month
    if (Integer.parseInt(date_and_year.substring(5, 7)) < 0
        || Integer.parseInt(date_and_year.substring(5, 7)) > 12) {
      return false;
    }
    // check hours
    if (Integer.parseInt(hours_and_minutes.substring(0, 2)) < 0
        || Integer.parseInt(hours_and_minutes.substring(0, 2)) > 24) {
      return false;
    }
    // check minutes
    if (Integer.parseInt(hours_and_minutes.substring(3, 5)) < 0
        || Integer.parseInt(hours_and_minutes.substring(3, 5)) > 60) {
      return false;
    }
    return true;
  }

  public boolean isTotalValid(Number total) {
    boolean failPrecision = (BigDecimal.valueOf((Double) total).scale() != 2);
    return (int) total > 0 && !failPrecision;
  }
}
