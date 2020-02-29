package bed;

import static spark.Spark.get;
import static spark.Spark.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class BedResource implements RouteGroup {
  public static final String ROOT_PATH = "/beds";
  private BedService bedService = new BedService();

  @Override
  public void addRoutes() {
    get("", this::getBeds, new ObjectMapper()::writeValueAsString);

    post(
        "",
        (request, response) -> {
          try {
            response.type("application/json");
            ObjectMapper mapper = new ObjectMapper();
            Bed bed = mapper.readValue(request.body(), Bed.class);
            String uuid = bedService.addBed(bed);
            return uuid;
          } catch (Exception e) {
            response.status(400);
            return e.toString();
          }
        });
  }

  public Object getBeds(Request request, Response response) {

    String packageNames = request.queryParamOrDefault("package", "empty");
    String bedTypes = request.queryParamOrDefault("bedType", "empty");
    String cleaningFrequencies = request.queryParamOrDefault("cleaningFreq", "empty");
    String bloodTypes = request.queryParamOrDefault("bloodTypes", "empty");
    String minCapacity = request.queryParamOrDefault("minCapacity", "0");
    Query query = new Query(packageNames, bedTypes, cleaningFrequencies, bloodTypes, minCapacity);
    ArrayList<Bed> beds = this.bedService.Get(query);

    response.status(HttpStatus.OK_200);
    return beds;
  }
}
