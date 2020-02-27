import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.UnserialiazableObjectException;

public class Serializer {
  public static String dataToJson(Object data) throws UnserialiazableObjectException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(data);
    } catch (Exception e) {
      throw new UnserialiazableObjectException("Champs privés sans getter/setter");
    }
  }
}
