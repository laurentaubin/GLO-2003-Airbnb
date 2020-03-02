import static spark.Spark.path;

import transactions.TransactionResource;

public class Router {
  public static void setUpRoutes() {
    path(RootResource.ROOT_PATH, new RootResource());
    path(TransactionResource.TRANSACTION_PATH, new TransactionResource());
  }
}
