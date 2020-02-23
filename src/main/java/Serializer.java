import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer {
  public static String dataToJson(Object data) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(data);
    } catch (Exception e) {
      System.out.println(e);
      throw new RuntimeException();
    }
  }
}
