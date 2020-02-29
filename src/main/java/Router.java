import static spark.Spark.path;

import bed.BedResource;
import testclass.MovieResource;

public class Router {
  public static void setUpRoutes() {
    path(BedResource.ROOT_PATH, new BedResource());
    path(MovieResource.ROOT_PATH, new MovieResource());
  }
}
