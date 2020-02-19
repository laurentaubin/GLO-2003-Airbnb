import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StatusResponseTest {

  @Test
  void createStatusResponse_withSuccessStatusGiven_shouldAssignSuccessAsStatus() {
    StatusResponse statusResponse = StatusResponse.SUCCESS;
    assertEquals(statusResponse.getStatus(), "Success");
  }

  @Test
  void createStatusResponse_withErrorStatusGiven_shouldAssignErrorAsStatus() {
    StatusResponse statusResponse = StatusResponse.ERROR;
    assertEquals(statusResponse.getStatus(), "Error");
  }
}
