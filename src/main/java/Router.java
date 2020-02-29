import static spark.Spark.path;

import bed.BedResource;

public class Router {
  public static void setUpRoutes() {
    path(BedResource.ROOT_PATH, new BedResource());
  }
}
