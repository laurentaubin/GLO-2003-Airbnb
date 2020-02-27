package bed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

import static spark.Spark.get;

public class BedResource implements RouteGroup {
    public static final String ROOT_PATH = "/admin/transaction";


    @Override
    public void addRoutes() {
        get("/hello", this::helloWorld, new ObjectMapper()::writeValueAsString);
    }

    public Object helloWorld(Request request, Response response) {
        response.status(HttpStatus.OK_200);
        return "Hello World";
    }
}