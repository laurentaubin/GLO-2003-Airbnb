package bed;

import static spark.Spark.get;
import static spark.Spark.post;

import bed.response.ErrorPostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.BedException;
import exceptions.bed.BedService.InvalidUuidException;
import exceptions.bed.MinimalCapacity.InvalidMinCapacityException;
import java.util.ArrayList;
import java.util.UUID;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class BedResource implements RouteGroup {
  public static final String ROOT_PATH = "/beds";
  private BedService bedService = BedService.getInstance();
  private JsonToBedConverter jsonToBedConverter = new JsonToBedConverter();
  private ObjectMapper objectMapper = new ObjectMapper();
  private BedValidator bedValidator = new BedValidator();

  @Override
  public void addRoutes() {
    get("", this::getBeds, new ObjectMapper()::writeValueAsString);

    post(
        "",
        (request, response) -> {
          try {
            response.type("application/json");
            bedValidator.validateBed(request.body());
            Bed bed = jsonToBedConverter.generateBedFromJson(request.body());
            String uuid = UUID.randomUUID().toString();
            bed.setUuid(uuid);
            bedService.addBed(bed, uuid);
            response.status(201);
            response.header("Location", "/beds/:" + uuid);
            return uuid;
          } catch (BedException e) {
            response.status(400);
            return generatePostErrorMessage(e);
          }
        });

    get("/:uuid", this::getBed, new ObjectMapper()::writeValueAsString);
  }

  public Object getBed(Request request, Response response) {

    String uuid = request.params(":uuid");

    try {
      Bed bed = this.bedService.getBedByUuid(uuid);
      response.status(HttpStatus.OK_200);
      return bed;

    } catch (InvalidUuidException e) {
      ErrorHandler error =
          new ErrorHandler(
              "BED_NOT_FOUND", String.format("bed with number %s could not be found", uuid));
      response.status(HttpStatus.NOT_FOUND_404);
      return error;
    }
  }

  public Object getBeds(Request request, Response response) throws JsonProcessingException {
    try {
      String packageNames = request.queryParamOrDefault("package", "empty");
      String bedTypes = request.queryParamOrDefault("bedType", "empty");
      String cleaningFrequencies = request.queryParamOrDefault("cleaningFreq", "empty");
      String bloodTypes = request.queryParamOrDefault("bloodTypes", "empty");
      String minCapacity = request.queryParamOrDefault("minCapacity", "0");

      if (Integer.parseInt(minCapacity) < 0) {
        throw new InvalidMinCapacityException();
      }

      Query query = new Query(packageNames, bedTypes, cleaningFrequencies, bloodTypes, minCapacity);
      ArrayList<Bed> beds = this.bedService.Get(query);

      ArrayList<BedsResponse> bedsResponses = new ArrayList<BedsResponse>();
      for (Bed bed : beds) {
        BedsResponse bedResponse =
            new BedsResponse(
                bed.fetchUuid(),
                bed.getZipCode(),
                bed.getBedType(),
                bed.getCleaningFrequency(),
                bed.getBloodTypes(),
                bed.getCapacity(),
                bed.getStars(),
                bed.getPackages());
        bedsResponses.add(bedResponse);
      }

      response.status(HttpStatus.OK_200);
      return bedsResponses;

    } catch (BedException e) {
      ErrorGetResponse errorGetResponse = new ErrorGetResponse();
      errorGetResponse.setDescription(e.getDescription());
      errorGetResponse.setError(e.getError());
      return errorGetResponse;
    }
  }

  private String generatePostErrorMessage(BedException e) throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(e.getError());
    errorPostResponse.setDescription(e.getDescription());
    return objectMapper.writeValueAsString(errorPostResponse);
  }
}
