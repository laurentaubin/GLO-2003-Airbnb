package bed;

import static spark.Spark.get;
import static spark.Spark.post;

import bed.booking.Booking;
import bed.booking.BookingResponse;
import bed.booking.BookingService;
import bed.booking.BookingValidator;
import bed.booking.JsonToBookingConverter;
import bed.response.ErrorPostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.BedException;
import exceptions.bed.MinimalCapacity.InvalidMinCapacityException;
import exceptions.booking.BedNotFoundException;
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

  private BookingService bookingService = BookingService.getInstance();
  private JsonToBookingConverter jsonToBookingConverter = new JsonToBookingConverter();
  private BookingValidator bookingValidator = new BookingValidator();

  @Override
  public void addRoutes() {
    get("", this::getBeds);

    post(
        "",
        (request, response) -> {
          try {
            bedValidator.validateBed(request.body());
            Bed bed = jsonToBedConverter.generateBedFromJson(request.body());
            String uuid = UUID.randomUUID().toString();
            bed.setUuid(uuid);
            bedService.addBed(bed, uuid);
            response.status(201);
            response.header("Location", "/beds/" + uuid);
            return objectMapper.writeValueAsString(uuid);
          } catch (BedException e) {
            response.status(400);
            return generatePostErrorMessage(e);
          }
        });

    get("/:uuid", (this::getBed));

    get("/:uuid/bookings", this::getAllBookings);

    get("/:uuid/bookings/:bookingUuid", this::getBooking);
    post(
        "/:uuid/bookings",
        (request, response) -> {
          try {
            Bed bed = this.bedService.getBedByUuid(request.params(":uuid"));
            bookingValidator.validateBooking(request.body(), bed);
            Booking booking = jsonToBookingConverter.generateBookingFromJson(request.body());
            String bookingUuid = bed.addBooking(booking);
            response.status(201);
            response.header("Location", "/beds/" + bed.getUuid() + "/bookings/" + bookingUuid);
            return objectMapper.writeValueAsString(bookingUuid);
          } catch (BedException e) {
            return generatePostErrorMessage(e);
          }
        });
  }

  public Object getBed(Request request, Response response) throws JsonProcessingException {

    String uuid = request.params(":uuid");

    try {
      BedResponse bedResponse = this.bedService.getBedResponseByUuid(uuid);
      response.status(HttpStatus.OK_200);
      return objectMapper.writeValueAsString(bedResponse);

    } catch (BedException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    }
  }

  public Object getBeds(Request request, Response response) throws JsonProcessingException {
    try {
      String packageNames = request.queryParamOrDefault("package", "empty");
      String bedTypes = request.queryParamOrDefault("bedType", "empty");
      String cleaningFrequencies = request.queryParamOrDefault("cleaningFreq", "empty");
      String bloodTypes = request.queryParamOrDefault("bloodTypes", "empty");
      String minCapacity = request.queryParamOrDefault("minCapacity", "1");

      if (minCapacity.indexOf('.') != -1 || Integer.parseInt(minCapacity) <= 0) {
        throw new InvalidMinCapacityException();
      }

      Query query = new Query(packageNames, bedTypes, cleaningFrequencies, bloodTypes, minCapacity);
      ArrayList<Bed> beds = this.bedService.Get(query);

      ArrayList<BedResponse> bedsResponses = new ArrayList<BedResponse>();
      for (Bed bed : beds) {
        BedResponse bedResponse =
            new BedResponse(
                bed.getUuid(),
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
      return objectMapper.writeValueAsString(bedsResponses);

    } catch (BedException e) {
      response.status(400);
      return generatePostErrorMessage(e);
    }
  }

  private String generatePostErrorMessage(BedException e) throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(e.getError());
    errorPostResponse.setDescription(e.getDescription());
    return objectMapper.writeValueAsString(errorPostResponse);
  }

  public Object getBooking(Request request, Response response) throws JsonProcessingException {
    try {
      BookingResponse booking =
          this.bedService.getBookingResponseByUuid(
              request.params(":uuid"), request.params(":bookingUuid"));
      response.status(200);
      return objectMapper.writeValueAsString(booking);
    } catch (BedException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    }
  }

  public Object getAllBookings(Request request, Response response) throws JsonProcessingException {
    try {
      ArrayList<BookingResponse> bookings =
          this.bedService.getAllBookingsForBed(request.params(":uuid"));
      response.status(200);
      return objectMapper.writeValueAsString(bookings);
    } catch (BedNotFoundException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    } catch (BedException e) {
      response.status(400);
      return generatePostErrorMessage(e);
    }
  }
}
