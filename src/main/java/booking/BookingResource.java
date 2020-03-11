package booking;

import static spark.Spark.get;
import static spark.Spark.post;

import bed.BedPackage;
import bed.BedService;
import bed.ErrorHandler;
import bed.response.ErrorPostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.BookingException;
import java.text.DecimalFormat;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class BookingResource implements RouteGroup {
  public static final String ROOT_PATH = "/beds/:uuid/bookings";
  private BookingService bookingService = BookingService.getInstance();
  private JsonToBookingConverter jsonToBookingConverter = new JsonToBookingConverter();
  private ObjectMapper objectMapper = new ObjectMapper();
  private BookingValidator bookingValidator = new BookingValidator();
  private BedService bedService = BedService.getInstance();

  @Override
  public void addRoutes() {
    post(
        "",
        (request, response) -> {
          try {
            response.type("application/json");
            String bedNumber = request.params(":uuid");
            bookingValidator.validateBooking(request.body(), bedNumber);
            Booking booking = jsonToBookingConverter.generateBookingFromJson(request.body());
            String bookingUuid = bookingService.addBooking(booking);
            bookingService.addBookingForSpecificBed(bedNumber, booking);
            response.status(201);
            response.header("Location", "/beds/" + bedNumber + "/bookings/" + bookingUuid);
            return bookingUuid;
          } catch (BookingException e) {
            return generatePostErrorMessage(e);
          }
        });

    get("/:bookingUuid", this::getBooking, new ObjectMapper()::writeValueAsString);
  }

  public Object getBooking(Request request, Response response) {
    String bookingUuid = request.params(":bookingUuid");
    String bedNumber = request.params(":uuid");
    float total = 0.0f;
    try {
      Booking booking = this.bookingService.getBookingByUuid(bookingUuid);
      BedPackage[] bedPackages = bedService.getBedByUuid(bedNumber).getPackages();
      for (BedPackage bedPackage : bedPackages) {
        if (bedPackage.getName().toString().equals(booking.getBedPackage())) {
          double pricePerNight = bedPackage.getPricePerNight();
          total =
              Float.parseFloat(
                  new DecimalFormat("##.##").format(pricePerNight * booking.getNumberOfNights()));
        }
      }
      BookingDummyObject bookingDummyObject =
          new BookingDummyObject(
              booking.getArrivalDate(),
              booking.getNumberOfNights(),
              booking.getBedPackage(),
              total);
      response.status(201);
      return bookingDummyObject;
    } catch (exceptions.booking.BookingService.InvalidUuidException e) {
      ErrorHandler error =
          new ErrorHandler(
              "BOOKING_NOT_FOUND",
              String.format("booking with number %s could not be found", bookingUuid));
      response.status(404);
      return error;
    }
  }

  private String generatePostErrorMessage(BookingException e) throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(e.getError());
    errorPostResponse.setDescription(e.getDescription());
    return objectMapper.writeValueAsString(errorPostResponse);
  }
}
