package domain.geolocation;

import domain.geolocation.exception.InvalidZipCodeException;
import domain.geolocation.exception.NonExistingZipCodeException;
import java.awt.geom.Point2D;
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

  public static Point2D getZipCodeCoordinates(String zipCode) throws IOException {
    validateZipCode(zipCode);
    JsonNode response = GETRequest(zipCode);

    Double lat = Double.parseDouble(response.get("places").get(0).get("latitude").asText());
    Double lng = Double.parseDouble(response.get("places").get(0).get("longitude").asText());

    Point2D coordinates = new Point2D.Double(lat, lng);

    return coordinatesDegreeConverter(coordinates);
  }

  public static Point2D coordinatesDegreeConverter(Point2D coordinates) throws IOException {
    double latitudeDegree = 110.574;
    double longitudeDegree = 111.320;

    double lat = coordinates.getX();
    double lon = coordinates.getY();

    double x = lat * latitudeDegree;
    double y = lon * longitudeDegree * Math.cos(Math.toRadians(lat));

    Point2D latLongConvertedToDegrees = new Point2D.Double(x, y);
    return latLongConvertedToDegrees;
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
