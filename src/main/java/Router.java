import static spark.Spark.path;

import presentation.bed.BedResource;
import presentation.transaction.TransactionResource;

public class Router {
  public static void setUpRoutes() {
    path(BedResource.ROOT_PATH, new BedResource());
    path(TransactionResource.TRANSACTION_PATH, new TransactionResource());
  }
}
