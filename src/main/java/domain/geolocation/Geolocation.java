package domain.geolocation;

import domain.geolocation.exception.InvalidZipCodeException;
import domain.geolocation.exception.NonExistingZipCodeException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class Geolocation {

  public static boolean validateZipCode(String zipCode) throws IOException {
    if (!(zipCode.matches("[0-9]+") && zipCode.length() == 5)) {
      throw new InvalidZipCodeException();
    } else if (GETRequest(zipCode) == null) {
      throw new NonExistingZipCodeException();
    }
    return true;
  }

  public static JsonNode getLocation(String zipCode) throws IOException {
    validateZipCode(zipCode);
    JsonNode response = GETRequest(zipCode);
    return response.get("places").get(0);
  }

  public static JsonNode GETRequest(String zipCode) throws IOException {
    URL url = new URL("https://api.zippopotam.us/US/" + zipCode);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.connect();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode response = null;
    if (request.getResponseCode() != 404) {
      response = objectMapper.readTree(new InputStreamReader((InputStream) request.getContent()));
    }
    return response;
  }
}
