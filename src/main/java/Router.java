import static spark.Spark.path;

import bed.BedResource;
import transactions.TransactionResource;

public class Router {
  public static void setUpRoutes() {
    path(BedResource.ROOT_PATH, new BedResource());
    path(TransactionResource.TRANSACTION_PATH, new TransactionResource());
  }
}
