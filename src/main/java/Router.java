import static spark.Spark.path;

public class Router {
  public static void setUpRoutes() {
    path(RootRessource.ROOT_PATH, new RootRessource());
  }
}
