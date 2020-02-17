import static spark.Spark.*;

public class Main {

  public static void main(String[] args) {
    port(Server.getHerokuAssignedPort());
    Router.setUpRoutes();
    CORS.enable("*", Server.CORS_HEADERS, Server.CORS_METHODS);
  }
}
