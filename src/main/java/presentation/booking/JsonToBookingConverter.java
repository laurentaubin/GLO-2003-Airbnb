package presentation.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import domain.booking.Booking;
import java.io.IOException;

public class JsonToBookingConverter {

  private ObjectMapper mapper;

  public JsonToBookingConverter() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("BookingDeserializer");
    module.addDeserializer(Booking.class, new BookingDeserializer());
    mapper.registerModule(module);
  }

  public Booking generateBookingFromJson(String json) throws IOException {
    return this.mapper.readValue(json, Booking.class);
  }
}
