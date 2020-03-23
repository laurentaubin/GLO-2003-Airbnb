package bed;

import static spark.Spark.get;
import static spark.Spark.post;

import bed.booking.Booking;
import bed.booking.BookingResponse;
import bed.booking.BookingTotalPriceCalculator;
import bed.booking.BookingValidator;
import bed.booking.JsonToBookingConverter;
import bed.booking.exception.BedNotFoundException;
import bed.booking.exception.BookingNotFoundException;
import bed.exception.InvalidMinCapacityException;
import bed.response.ErrorPostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            return uuid;
          } catch (AirbnbException e) {
            response.status(400);
            return generatePostErrorMessage(e);
          }
        });

    get("/:uuid", (this::getBed), this.objectMapper::writeValueAsString);

    get("/:uuid/bookings", this::getAllBookings, this.objectMapper::writeValueAsString);

    get("/:uuid/bookings/:bookingUuid", this::getBooking, this.objectMapper::writeValueAsString);
    post(
        "/:uuid/bookings",
        (request, response) -> {
          try {
            Bed bed = this.bedService.getBedByUuid(request.params(":uuid"));
            bookingValidator.validateBooking(request.body(), bed);
            Booking booking = jsonToBookingConverter.generateBookingFromJson(request.body());
            BookingTotalPriceCalculator calculator =
                new BookingTotalPriceCalculator(bed.getPackages(), booking);
            booking.setTotal(calculator.getTotalWithDiscount());
            String bookingUuid = bed.addBooking(booking);
            response.status(201);
            response.header("Location", "/beds/" + bed.getUuid() + "/bookings/" + bookingUuid);
            return bookingUuid;
          } catch (AirbnbException e) {
            return generatePostErrorMessage(e);
          }
        });

    post(
        "/:uuid/bookings/:bookingUuid/cancel",
        ((request, response) -> {
          try {
            System.out.println("\n\n\n BOOKING IS BEING CANCELED \n\n\n");
            String bedUuid = request.params(":uuid");
            String bookingUuid = request.params(":bookingUuid");
            this.bedService.cancelBooking(bedUuid, bookingUuid);
            return "Booking " + bookingUuid + " has been canceled";
          } catch (BedNotFoundException e) {
            response.status(404);
            return generatePostErrorMessage(e);
          } catch (BookingNotFoundException e) {
            response.status(404);
            return generatePostErrorMessage(e);
          } catch (AirbnbException e) {
            response.status(400);
            return generatePostErrorMessage(e);
          }
        }));
  }

  public Object getBed(Request request, Response response) throws JsonProcessingException {

    String uuid = request.params(":uuid");

    try {
      BedResponse bedResponse = this.bedService.getBedResponseByUuid(uuid);
      response.status(HttpStatus.OK_200);
      return bedResponse;

    } catch (AirbnbException e) {
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
                bed.getPackages(),
                bed.getLodgingMode());
        bedsResponses.add(bedResponse);
      }

      response.status(HttpStatus.OK_200);
      return bedsResponses;

    } catch (AirbnbException e) {
      response.status(400);
      return generatePostErrorMessage(e);
    }
  }

  private ErrorPostResponse generatePostErrorMessage(AirbnbException e) {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(e.getError());
    errorPostResponse.setDescription(e.getDescription());
    return errorPostResponse;
  }

  public Object getBooking(Request request, Response response) {
    try {
      BookingResponse booking =
          this.bedService.getBookingResponseByUuid(
              request.params(":uuid"), request.params(":bookingUuid"));
      response.status(200);
      return booking;
    } catch (AirbnbException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    }
  }

  public Object getAllBookings(Request request, Response response) {
    try {
      ArrayList<BookingResponse> bookings =
          this.bedService.getAllBookingsForBed(request.params(":uuid"));
      response.status(200);
      return bookings;
    } catch (BookingNotFoundException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    } catch (AirbnbException e) {
      response.status(400);
      return generatePostErrorMessage(e);
    }
  }
}
