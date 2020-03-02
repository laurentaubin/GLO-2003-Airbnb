package booking;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import exceptions.booking.BookingEnumException;
import java.io.IOException;

public class BookingDeserializer extends JsonDeserializer<Booking> {
  BookingDeserializer() {
    super();
  }

  @Override
  public Booking deserialize(JsonParser parser, DeserializationContext deserializer)
      throws IOException, BookingEnumException {
    JsonNode bookingNode;

    bookingNode = parser.getCodec().readTree(parser);
    Booking booking = new Booking();

    booking.setTenantPublicKey(bookingNode.get("tenantPublicKey").textValue());
    booking.setArrivalDate(bookingNode.get("arrivalDate").textValue());
    booking.setNumberOfNights(bookingNode.get("numberOfNights").asInt());
    booking.setBedPackage(bookingNode.get("bedPackage").textValue());

    return booking;
  }
}
