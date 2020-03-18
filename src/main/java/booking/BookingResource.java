package booking;

import static spark.Spark.get;
import static spark.Spark.post;

import bed.BedPackage;
import bed.BedService;
import bed.response.ErrorPostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.BedException;
import java.math.BigDecimal;
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
            String bedNumber = request.params(":uuid");
            bookingValidator.validateBooking(request.body(), bedNumber);
            Booking booking = jsonToBookingConverter.generateBookingFromJson(request.body());
            String bookingUuid = bookingService.addBooking(booking);
            bookingService.addBookingForSpecificBed(bedNumber, booking);
            response.status(201);
            response.header("Location", "/beds/" + bedNumber + "/bookings/" + bookingUuid);
            return bookingUuid;
          } catch (BedException e) {
            return generatePostErrorMessage(e);
          }
        });

    get("/:bookingUuid", this::getBooking, new ObjectMapper()::writeValueAsString);
  }

  public Object getBooking(Request request, Response response) throws JsonProcessingException {
    String bookingUuid = request.params(":bookingUuid");
    String bedNumber = request.params(":uuid");
    try {
      Booking booking = this.bookingService.getBookingByUuid(bookingUuid);
      BedPackage[] bedPackages = bedService.getBedByUuid(bedNumber).getPackages();
      BookingTotalPriceCalculator priceCalculator =
          new BookingTotalPriceCalculator(bedPackages, booking);
      BigDecimal total = priceCalculator.getTotalWithDiscount();
      BookingDummyObject bookingDummyObject =
          new BookingDummyObject(
              booking.getArrivalDate(),
              booking.getNumberOfNights(),
              booking.getBedPackage(),
              total);
      response.status(200);
      return bookingDummyObject;
    } catch (BedException e) {
      response.status(404);
      return generatePostErrorMessage(e);
    }
  }

  private String generatePostErrorMessage(BedException e) throws JsonProcessingException {
    ErrorPostResponse errorPostResponse = new ErrorPostResponse();
    errorPostResponse.setError(e.getError());
    errorPostResponse.setDescription(e.getDescription());
    return objectMapper.writeValueAsString(errorPostResponse);
  }
}
