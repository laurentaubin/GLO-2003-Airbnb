package Bed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedServiceTest {
  private BedService bedService;

  @BeforeEach
  void setUp() {
    this.bedService = new BedService();
  }

  @Test
  void addingBed_withValidParameters_shouldNotThrow() throws Exception {
    Bed dummyBed = new Bed();

    bedService.add(dummyBed);
  }
}
