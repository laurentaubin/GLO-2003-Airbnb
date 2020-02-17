import static spark.Spark.get;
import static spark.Spark.path;

import spark.RouteGroup;

public class RootResource implements RouteGroup {
  public static final String ROOT_PATH = "/";

  @Override
  public void addRoutes() {
    path("/", () -> get("/hello", (req, res) -> "Air Bed and Bugs GLO2003"));
  }
}
