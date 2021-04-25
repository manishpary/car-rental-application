package co.uk.codetest.carrentalapi;

import co.uk.codetest.carrentalapi.service.CarRentalBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CarRentalApiStartupRunnerTest {

  @SpyBean private CarRentalBookingService carRentalBookingService;

  @Test
  void testCarRentalApiStartupRunner() throws Exception {
    verify(carRentalBookingService, times(1)).calculateTripFare(any(), any(), any(), any(), any());
  }
}
