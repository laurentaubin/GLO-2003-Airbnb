import static spark.Spark.path;

import bed.BedResource;

public class Router {
  public static void setUpRoutes() {
    path(RootResource.ROOT_PATH, new RootResource());
    path(BedResource.ROOT_PATH, new BedResource());
  }
}
