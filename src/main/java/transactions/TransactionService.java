package transactions;

import java.util.ArrayList;

public class TransactionService {
    private ArrayList<Transaction> listOfTransactions = new ArrayList<Transaction>();

    public TransactionService() {}

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
