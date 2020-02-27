package transactions;

public class Transaction {
    private String from_uuid;
    private String to_uuid;
    private float total;
    private String reason;

    public Transaction(String from_uuid, String to_uuid, float total, String reason) {
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
