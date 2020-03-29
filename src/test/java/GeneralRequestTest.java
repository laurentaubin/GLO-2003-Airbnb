import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GeneralRequestTest {

  private String httpPath = "http://localhost:" + Server.getHerokuAssignedPort();

  private String bedsHttpPath = httpPath + "/beds";

  @BeforeAll
  static void startServer_UsingHerokuAssignedPort() {
    Main.main(new String[] {"a", "b"});
  }

  @AfterAll
  static void stopServer() {
    Server.stop();
  }

  @Test
  public void givenPathDoesNotExists_whenAPiIsCalled_then404IsReceived() throws IOException {

    String name = RandomStringUtils.randomAlphabetic(8);
    HttpUriRequest request = new HttpGet(httpPath + "/" + name);

    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

    assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
  }

  @Test
  public void
      givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson()
          throws IOException {

    String jsonMimeType = "application/json";
    HttpUriRequest request = new HttpGet(httpPath);

    HttpResponse response = HttpClientBuilder.create().build().execute(request);

    String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
    assertEquals(jsonMimeType, mimeType);
  }

  @Test
  public void givenPathExists_whenAPiIsCalled_then200IsReceived() throws IOException {

    HttpUriRequest request = new HttpGet(bedsHttpPath);

    HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

    assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
  }
}
