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
        get("/", ((request, response) -> {
            response.status(200);
            response.type("application/json");
            return (transactionService.getListOfTransactions());
    }));
    }
}