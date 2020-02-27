import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.Serializer.UnserialiazableObjectException;

public class Serializer {
  public static String dataToJson(Object data) throws UnserialiazableObjectException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(data);
    } catch (Exception e) {
      throw new UnserialiazableObjectException(
          "Private object with no getter/setter causes this exception");
    }
  }
}
