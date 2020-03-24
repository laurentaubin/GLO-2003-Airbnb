package presentation.transaction;

import static spark.Spark.get;

import application.transaction.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class TransactionResource implements RouteGroup {
  public static final String TRANSACTION_PATH = "/admin/transactions";
  private TransactionService transactionService = TransactionService.getInstance();
  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public void addRoutes() {
    get("", this::getTransactions, this.mapper::writeValueAsString);
  }

  private Object getTransactions(Request request, Response response) {
    return this.transactionService.getTransactions();
  }
}
