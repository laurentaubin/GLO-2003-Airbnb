package transactions;

import static spark.Spark.get;

import spark.RouteGroup;

public class TransactionResource implements RouteGroup {
  public static final String TRANSACTION_PATH = "/admin/transactions";

  public static TransactionService transactionService = new TransactionService();

  @Override
  public void addRoutes() {
    get(
        "",
        ((request, response) -> {
          response.status(200);
          response.type("application/json");
          return (transactionService.getListOfTransactions());
        }));
  }
}
