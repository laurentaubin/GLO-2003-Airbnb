package transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

import java.io.IOException;
import java.io.StringWriter;

import static spark.Spark.get;

public class TransactionResource implements RouteGroup {
    public static final String TRANSACTION_PATH = "/admin/transactions";

    public static TransactionService transactionService = new TransactionService();

    @Override
    public void addRoutes() {
        get("/helloworld", ((request, response) -> {
            response.status(200);
            response.type("application/json");
            return (transactionService.getTotalNumberOfTransactions());
    }));
    }

//    public static String dataToJson(Object data) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.enable(SerializationFeature.INDENT_OUTPUT);
//            StringWriter sw = new StringWriter();
//            mapper.writeValue(sw, data);
//            return sw.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("IOException from a StringWriter?");
//        }
//    }

//    public Object helloWorld(Request request, Response response) {
//        response.status(HttpStatus.OK_200);
//        return "Hello World";
//    }
}