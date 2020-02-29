package bed;

import static spark.Spark.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class BedResource implements RouteGroup {
  public static final String ROOT_PATH = "/beds/";
  private BedService bedService = new BedService();

  @Override
  public void addRoutes() {
    get("/", this::getBeds, new ObjectMapper()::writeValueAsString);
  }

  public Object getBeds(Request request, Response response) {

    PackageName[] packageNames;
    String _packageName = request.queryParamOrDefault("package", "empty");
    if (_packageName.equals("empty")) {
      packageNames =
          new PackageName[] {
            PackageName.ALL_YOU_CAN_DRINK, PackageName.BLOOD_THIRSTY, PackageName.SWEET_TOOTH
          };
    } else {
      packageNames = new PackageName[] {PackageName.valueOf(_packageName)};
    }

    BedType[] bedTypes;
    String _bedType = request.queryParamOrDefault("bedType", "empty");
    if (_bedType.equals("empty")) {
      bedTypes = new BedType[] {BedType.LATEX, BedType.MEMORY_FOAM, BedType.SPRINGS};
    } else {
      bedTypes = new BedType[] {BedType.valueOf(_bedType)};
    }

    CleaningFrequency[] cleaningFrequencies;
    String _cleaningFrequency = request.queryParamOrDefault("cleaningFreq", "empty");
    if (_cleaningFrequency.equals("empty")) {
      cleaningFrequencies =
          new CleaningFrequency[] {
            CleaningFrequency.ANNUAL,
            CleaningFrequency.MONTHLY,
            CleaningFrequency.WEEKLY,
            CleaningFrequency.NEVER
          };
    } else {
      cleaningFrequencies = new CleaningFrequency[] {CleaningFrequency.valueOf(_cleaningFrequency)};
    }

    BloodType[] bloodTypes;
    String _bloodTypes = request.queryParamOrDefault("bloodTypes", "empty");
    if (_bloodTypes.equals("empty")) {
      bloodTypes = new BloodType[0];
    } else {
      String[] bloodTypesStrings = _bloodTypes.split(",");
      ArrayList<BloodType> bloodTypesList = new ArrayList<>();
      for (String bloodType : bloodTypesStrings) {
        bloodTypesList.add(BloodType.valueOf(bloodType));
      }
      bloodTypes = bloodTypesList.toArray(new BloodType[bloodTypesStrings.length]);
    }

    int minCapacity = Integer.parseInt(request.queryParamOrDefault("minCapacity", "0"));

    Query query = new Query(packageNames, bedTypes, cleaningFrequencies, bloodTypes, minCapacity);
    ArrayList<Bed> beds = this.bedService.Get(query);

    response.status(HttpStatus.OK_200);
    return beds;
  }
}
