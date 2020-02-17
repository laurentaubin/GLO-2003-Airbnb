import static spark.Spark.path;

public class Router {
  public static void setUpRoutes() {
    path(RootResource.ROOT_PATH, new RootResource());
  }
}
