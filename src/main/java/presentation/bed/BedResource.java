package presentation.bed;

import static spark.Spark.get;
import static spark.Spark.post;

import application.Query;
import application.bed.BedService;
import application.bed.BedStarCalculator;
import application.bed.BedValidator;
import application.booking.BookingValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.bed.Bed;
import domain.bed.exception.AirbnbException;
import domain.booking.Booking;
import domain.booking.exception.BedNotFoundException;
import domain.booking.exception.BookingNotFoundException;
import domain.transaction.TransactionService;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.eclipse.jetty.http.HttpStatus;
import presentation.ErrorPostResponse;
import presentation.booking.BookingResponse;
import presentation.booking.JsonToBookingConverter;
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
  private TransactionService transactionService = TransactionService.getInstance();

  @Override
  public void addRoutes() {
    get("", this::getBeds, this.objectMapper::writeValueAsString);

    post(
        "",
        ((request, response) -> {
          this.createBed(request, response);
          return "";
        }));

    get("/:uuid", this::getBed, this.objectMapper::writeValueAsString);

    get("/:uuid/bookings", this::getAllBookings, this.objectMapper::writeValueAsString);

    get("/:uuid/bookings/:bookingUuid", this::getBooking, this.objectMapper::writeValueAsString);

    post("/:uuid/bookings", this::makeBooking, this.objectMapper::writeValueAsString);

    post(
        "/:uuid/bookings/:bookingUuid/cancel",
        this::cancelBooking,
        this.objectMapper::writeValueAsString);
  }

  public Object getBed(Request request, Response response) {

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

  public Object getBeds(Request request, Response response) {
    try {
      String packageNames = request.queryParamOrDefault("package", "empty");
      String bedTypes = request.queryParamOrDefault("bedType", "empty");
      String cleaningFrequencies = request.queryParamOrDefault("cleaningFreq", "empty");
      String bloodTypes = request.queryParamOrDefault("bloodTypes", "empty");
      String minCapacity = request.queryParamOrDefault("minCapacity", "empty");
      String lodgingModes = request.queryParamOrDefault("lodgingMode", "empty");
      String arrivalDate = request.queryParamOrDefault("arrivalDate", "empty");
      String numberOfNights = request.queryParamOrDefault("numberOfNights", "empty");
      String origin = request.queryParamOrDefault("origin", "empty");
      String maxDistance = request.queryParamOrDefault("maxDistance", "empty");

      Query query =
          new Query(
              packageNames,
              bedTypes,
              cleaningFrequencies,
              bloodTypes,
              minCapacity,
              lodgingModes,
              arrivalDate,
              numberOfNights,
              origin,
              maxDistance);
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
                BedStarCalculator.calculateStars(bed),
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

  public Object cancelBooking(Request request, Response response) {
    try {
      String bedUuid = request.params(":uuid");
      String bookingUuid = request.params(":bookingUuid");
      this.bedService.cancelBooking(bedUuid, bookingUuid);
      return "Booking " + bookingUuid + " has been canceled";
    } catch (BedNotFoundException | BookingNotFoundException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    } catch (AirbnbException e) {
      response.status(400);
      return generatePostErrorMessage(e);
    }
  }

  public void createBed(Request request, Response response) throws IOException {
    try {
      this.bedValidator.validateBed(request.body());
      Bed bed = jsonToBedConverter.generateBedFromJson(request.body());
      String uuid = bedService.addBed(bed);
      response.status(201);
      response.header("Location", "/beds/" + uuid);
    } catch (AirbnbException e) {
      response.status(400);
      response.body(objectMapper.writeValueAsString(generatePostErrorMessage(e)));
    }
  }

  public Object makeBooking(Request request, Response response) throws IOException, ParseException {
    try {
      String bedUuid = request.params(":uuid");
      Bed bed = this.bedService.getBedByUuid(bedUuid);
      bookingValidator.validateBooking(request.body(), bed);
      Booking booking = jsonToBookingConverter.generateBookingFromJson(request.body());
      String bookingUuid = this.bedService.addBooking(bedUuid, booking);
      transactionService.addBookedTransactions(booking, bed);
      response.status(201);
      response.header("Location", "/beds/" + bed.getUuid() + "/bookings/" + bookingUuid);
      return bookingUuid;
    } catch (BedNotFoundException | BookingNotFoundException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    } catch (AirbnbException e) {
      response.status(400);
      return generatePostErrorMessage(e);
    }
  }
}
