package transactions;

import java.sql.Timestamp;

public class Transaction {
    private Timestamp timestamp;
    private String from_uuid;
    private String to_uuid;
    private Double total;
    private String reason;

    @Override
    public String toString() {
        return "{" +
                "timestamp:" + timestamp +
                ", from_uuid:'" + from_uuid + '\'' +
                ", to_uuid:'" + to_uuid + '\'' +
                ", total:" + total +
                ", reason:'" + reason + '\'' +
                '}';
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Transaction(String from_uuid, String to_uuid, Double total, String reason, Timestamp timestamp) {
        this.timestamp = timestamp;
        this.from_uuid = from_uuid;
        this.to_uuid = to_uuid;
        this.total = total;  //keep in mind only 2 digits... .2f marche pas
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

    public Double getTotal() {
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
