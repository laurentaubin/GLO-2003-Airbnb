package transactions;

public class Transaction {
  private String timestamp;
  private String from;
  private String to;
  private Number total;
  private String reason;

  public Transaction(String timestamp, String from, String to, Number total, String reason) {
    this.timestamp = timestamp;
    this.from = from;
    this.to = to;
    this.total = total;
    this.reason = reason;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public Number getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
