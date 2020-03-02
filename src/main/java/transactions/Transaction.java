package transactions;

import java.text.DecimalFormat;

public class Transaction {
  private String timestamp;
  private String from_uuid;
  private String to_uuid;
  private Number total;
  private String reason;

  @Override
  public String toString() {
    return "{"
        + "timestamp: '"
        + timestamp
        + '\''
        + ", from_uuid: '"
        + from_uuid
        + '\''
        + ", to_uuid: '"
        + to_uuid
        + '\''
        + ", total: "
        + total
        + ", reason: '"
        + reason
        + '\''
        + '}';
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Transaction(
      String from_uuid, String to_uuid, Number total, String reason, String timestamp) {
    String new_total = new DecimalFormat("##.##").format(total);
    this.timestamp = timestamp;
    this.from_uuid = from_uuid;
    this.to_uuid = to_uuid;
    this.total = Float.parseFloat(new DecimalFormat("##.##").format(total));
    this.reason = reason;
  }

  public String getFrom_uuid() {
    return from_uuid;
  }

  public void setFrom_uuid(String from_uuid) {
    this.from_uuid = from_uuid;
  }

  public String getTo_uuid() {
    return to_uuid;
  }

  public void setTo_uuid(String to_uuid) {
    this.to_uuid = to_uuid;
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
