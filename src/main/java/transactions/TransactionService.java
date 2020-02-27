package transactions;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionService {
    private ArrayList<Transaction> listOfTransactions = new ArrayList<Transaction>();
    private Transaction transaction1 = new Transaction("72001343BA93508E74E3BFFA68593C2016D0434CF0AA76CB3DF64F93170D60EC", "airbnb", 340.89, "STAY_BOOKED", "2050-05-21T15:23:20.142Z");
    private Transaction transaction2 = new Transaction("airbnb", "8F0436A6FB049085B7F19AB73933973BF21276276F2EC7D122AC110BB46A3A4E", 340.89, "STAY_COMPLETED", "2050-05-29T23:59:99.99999Z");


    public TransactionService() {
        this.listOfTransactions.add(transaction1);
        this.listOfTransactions.add(transaction2);
    }

    public ArrayList<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    public int getTotalNumberOfTransactions(){
        return listOfTransactions.size();
    }

    public void addTransaction(Transaction transaction){
        listOfTransactions.add(transaction);
    }

}
