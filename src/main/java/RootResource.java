import static spark.Spark.get;

import bed.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class RootResource implements RouteGroup {
  public static final String ROOT_PATH = "/";

  @Override
  public void addRoutes() {
    get("/hello", this::helloWorld, new ObjectMapper()::writeValueAsString);
    get("/beds", this::getBed, new ObjectMapper()::writeValueAsString);
  }

  public Object helloWorld(Request request, Response response) {
    response.status(HttpStatus.OK_200);
    return "Hello World";
  }

  public Object getBed(Request request, Response response) {

    PackageName packageName = PackageName.valueOf(request.queryParams("package"));
    BedType bedType = BedType.valueOf(request.queryParams("bedType"));
    CleaningFrequency cleaningFrequency =
        CleaningFrequency.valueOf(request.queryParams("cleaningFreq"));

    String[] bloodTypesStrings = request.queryParams("bloodTypes").split(",");
    ArrayList<BloodType> bloodTypesList = new ArrayList<>();
    for (String bloodType : bloodTypesStrings) {
      bloodTypesList.add(BloodType.valueOf(bloodType));
    }
    BloodType[] bloodTypes = bloodTypesList.toArray(new BloodType[bloodTypesStrings.length]);

    int minCapacity = Integer.parseInt(request.queryParams("minCapacity"));

    Query query = new Query(packageName, bedType, cleaningFrequency, bloodTypes, minCapacity);
    ArrayList<Bed> beds = BedService.Get(query);

    response.status(HttpStatus.OK_200);
    return beds;
  }
}
