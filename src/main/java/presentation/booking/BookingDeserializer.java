package presentation.booking;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import domain.booking.Booking;
import java.io.IOException;

public class BookingDeserializer extends JsonDeserializer<Booking> {
  BookingDeserializer() {
    super();
  }

  @Override
  public Booking deserialize(JsonParser parser, DeserializationContext deserializer)
      throws IOException {
    JsonNode bookingNode;

    bookingNode = parser.getCodec().readTree(parser);
    Booking booking =
        new Booking(
            bookingNode.get("tenantPublicKey").textValue(),
            bookingNode.get("arrivalDate").textValue(),
            bookingNode.get("numberOfNights").asInt(),
            bookingNode.get("package").textValue());
    return booking;
  }
}
