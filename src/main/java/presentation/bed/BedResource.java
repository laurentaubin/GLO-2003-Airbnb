package presentation.bed;

import static spark.Spark.get;
import static spark.Spark.post;

import application.Query;
import application.bed.BedService;
import application.bed.BedValidator;
import application.booking.BookingResponse;
import application.booking.BookingTotalPriceCalculator;
import application.booking.BookingValidator;
import application.transaction.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.bed.Bed;
import domain.bed.exception.AirbnbException;
import domain.bed.exception.InvalidMinCapacityException;
import domain.booking.Booking;
import domain.booking.exception.BedNotFoundException;
import domain.booking.exception.BookingNotFoundException;
import java.util.ArrayList;
import java.util.UUID;
import org.eclipse.jetty.http.HttpStatus;
import presentation.ErrorPostResponse;
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
            return objectMapper.writeValueAsString(generatePostErrorMessage(e));
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
            transactionService.addBookedTransactions(booking, bed);
            response.status(201);
            response.header("Location", "/beds/" + bed.getUuid() + "/bookings/" + bookingUuid);
            return bookingUuid;
          } catch (AirbnbException e) {
            return objectMapper.writeValueAsString(generatePostErrorMessage(e));
          }
        });

    post(
        "/:uuid/bookings/:bookingUuid/cancel",
        ((request, response) -> {
          try {
            String bedUuid = request.params(":uuid");
            String bookingUuid = request.params(":bookingUuid");
            this.bedService.cancelBooking(bedUuid, bookingUuid);
            return "Booking " + bookingUuid + " has been canceled";
          } catch (BedNotFoundException | BookingNotFoundException e) {
            response.status(404);
            return objectMapper.writeValueAsString(generatePostErrorMessage(e));
          } catch (AirbnbException e) {
            response.status(400);
            return objectMapper.writeValueAsString(generatePostErrorMessage(e));
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

  public Object getBeds(Request request, Response response) {
    try {
      String packageNames = request.queryParamOrDefault("package", "empty");
      String bedTypes = request.queryParamOrDefault("bedType", "empty");
      String cleaningFrequencies = request.queryParamOrDefault("cleaningFreq", "empty");
      String bloodTypes = request.queryParamOrDefault("bloodTypes", "empty");
      String minCapacity = request.queryParamOrDefault("minCapacity", "1");
      String lodgingModes = request.queryParamOrDefault("lodgingMode", "empty");

      if (minCapacity.indexOf('.') != -1 || Integer.parseInt(minCapacity) <= 0) {
        throw new InvalidMinCapacityException();
      }

      Query query =
          new Query(
              packageNames, bedTypes, cleaningFrequencies, bloodTypes, minCapacity, lodgingModes);
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
