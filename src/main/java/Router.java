import static spark.Spark.path;

import bed.BedResource;
import booking.BookingResource;
import transactions.TransactionResource;

public class Router {
  public static void setUpRoutes() {
    path(BedResource.ROOT_PATH, new BedResource());
    path(BookingResource.ROOT_PATH, new BookingResource());
    path(TransactionResource.TRANSACTION_PATH, new TransactionResource());
  }
}
