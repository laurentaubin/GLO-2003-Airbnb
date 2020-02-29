package bed;

import static spark.Spark.get;
import static spark.Spark.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.bed.InvalidOwnerKeyException;
import java.util.ArrayList;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class BedResource implements RouteGroup {
  public static final String ROOT_PATH = "/beds";
  private BedService bedService = new BedService();
  private ObjectMapper objectMapper = new ObjectMapper();

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
            if (!bedValidator.isPublicKeyValid(bed.getOwnerPublicKey())) {
              throw new InvalidOwnerKeyException();
            }
            String uuid = bedService.addBed(bed);
            response.status(201);
            response.header("Location", "/beds/:" + uuid);
            return uuid;
          } catch (InvalidOwnerKeyException e) {
            ErrorPostResponse errorPostResponse = new ErrorPostResponse();
            errorPostResponse.setError(PostStatusResponse.INVALID_PUBLIC_KEY);
            errorPostResponse.setDescription(
                "BiteCoins account public key should contain only "
                    + "alphanumeric characters and have a 256-bits length");
            return objectMapper.writeValueAsString(errorPostResponse);
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
