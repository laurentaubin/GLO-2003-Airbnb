package presentation.bed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import domain.bed.Bed;
import java.io.IOException;

public class JsonToBedConverter {

  private ObjectMapper mapper;

  JsonToBedConverter() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("BedDeserializer");
    module.addDeserializer(Bed.class, new BedDeserializer());
    mapper.registerModule(module);
  }

  public Bed generateBedFromJson(String json) throws IOException {
    return this.mapper.readValue(json, Bed.class);
  }
}
