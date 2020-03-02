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
  private BookingService bookingService = new BookingService();
  private JsonToBookingConverter jsonToBookingConverter = new JsonToBookingConverter();
  private ObjectMapper objectMapper = new ObjectMapper();
  private BookingValidator bookingValidator = new BookingValidator();
  private String bedNumber = "";
  private BedService bedService = BedService.getInstance();

  @Override
  public void addRoutes() {
    post(
        "",
        (request, response) -> {
          try {
            response.type("application/json");
            bedNumber = request.params(":uuid");
            Booking booking = jsonToBookingConverter.generateBookingFromJson(request.body());
            bookingValidator.validateBooking(booking, bedNumber);
            String bookingUuid = bookingService.addBooking(booking);
            response.status(201);
            response.header("Location", "/beds/:uuid/bookings/:" + bookingUuid);
            return bookingUuid;
          } catch (BookingException e) {
            return generatePostErrorMessage(e);
          } catch (Exception e) {
            return e.toString();
          }
        });

    get("/:bookingUuid", this::getBooking, new ObjectMapper()::writeValueAsString);
  }

  public Object getBooking(Request request, Response response) {
    String bookingUuid = request.params(":bookingUuid");
    String bedNumber = request.params(":uuid");
    Float total = 0.0f;
    try {
      Booking booking = this.bookingService.getBookingByUuid(bookingUuid);
      BedPackage[] bedPackages = bedService.getBedByUuid(bedNumber).getPackages();
      for (BedPackage bedPackage : bedPackages) {
        if (bedPackage.getName().toString().equals(booking.getBedPackage())) {
          Double pricePerNight = bedPackage.getPricePerNight();
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
      response.status(200);
      return bookingDummyObject;
    } catch (exceptions.booking.BookingService.InvalidUuidException e) {
      ErrorHandler error =
          new ErrorHandler(
              "BOOKING_NOT_FOUND",
              String.format("booking with number %s could not be found", bookingUuid));
      response.status(404);
      return error;
    } catch (Exception e) {
      return e.toString();
    }
  }

  private String generatePostErrorMessage(BookingException e) throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(e.getError());
    errorPostResponse.setDescription(e.getDescription());
    return objectMapper.writeValueAsString(errorPostResponse);
  }
}
