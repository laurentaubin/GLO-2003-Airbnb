package bed;

import static spark.Spark.get;
import static spark.Spark.post;

import bed.response.ErrorPostResponse;
import bed.response.PostStatusResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import exceptions.bed.BedService.InvalidUuidException;
=======
import exceptions.bed.CleaningFrequency.InvalidCleaningFrequencyException;
import exceptions.bed.InvalidCapacityException;
>>>>>>> Il manque comment tester les enums
import exceptions.bed.InvalidOwnerKeyException;
import exceptions.bed.InvalidZipCodeException;
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
            } else if (!bedValidator.isZipCodeValid(bed.getZipCode())) {
              throw new InvalidZipCodeException();
            } else if (!bedValidator.isCapacityValid(bed.getCapacity())) {
              throw new InvalidCapacityException();
            }

            String uuid = bedService.addBed(bed);
            response.status(201);
            response.header("Location", "/beds/:" + uuid);
            return uuid;
          } catch (InvalidOwnerKeyException e) {
            return invalidOwnerKeyErrorMessage();
          } catch (InvalidZipCodeException e) {
            return invalidZipCodeErrorMessage();
          } catch (InvalidCleaningFrequencyException e) {
            return invalidCleaningFrequencyErrorMessage();
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

  private String invalidOwnerKeyErrorMessage() throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(PostStatusResponse.INVALID_PUBLIC_KEY);
    errorPostResponse.setDescription(
        "BiteCoins account public key should contain only "
            + "alphanumeric characters and have a 256-bits length");
    return objectMapper.writeValueAsString(errorPostResponse);
  }

  private String invalidZipCodeErrorMessage() throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(PostStatusResponse.INVALID_ZIP_CODE);
    errorPostResponse.setDescription("zip code should be a 5 digits number");
    return objectMapper.writeValueAsString(errorPostResponse);
  }

  private String invalidCleaningFrequencyErrorMessage() throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(PostStatusResponse.INVALID_CLEANING_FREQUENCY);
    errorPostResponse.setDescription(
        "cleaning frequency should be one of weekly, monthly, annual or never");
    return objectMapper.writeValueAsString(errorPostResponse);
  }
}
