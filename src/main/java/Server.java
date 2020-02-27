import spark.Spark;

public class Server {

  public static final String CORS_HEADERS =
      "Content-Type,Authorization,X-Requested-With,Content-Length, Accept,Origin";
  public static final String CORS_METHODS = "GET,PUT,POST,DELETE,OPTIONS";

  static int getHerokuAssignedPort() {
//    ProcessBuilder processBuilder = new ProcessBuilder();
//    if (processBuilder.environment().get("PORT") != null) {
//      return Integer.parseInt(processBuilder.environment().get("PORT"));
//    }
    return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
  }

  static void stop() {
    Spark.stop();
  }
}
