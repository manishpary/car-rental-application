package co.uk.codetest.carrentalapi;

import co.uk.codetest.carrentalapi.service.CarRentalBookingService;
import co.uk.codetest.carrentalapi.util.DataUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CarRentalApiStartupRunner implements ApplicationRunner {
  @Autowired private CarRentalBookingService carRentalBookingService;

  @Autowired private DataUtil dataUtil;

  @Value("${car.rental.vehicltype:CAR}")
  @NonNull
  private String vehicleType;

  @Value("${car.rental.fueltype:PETROL}")
  @NonNull
  private String fuelType;

  @Value("${car.rental.destination:MUMBAI}")
  @NonNull
  private String destination;

  @Value("${car.rental.nooftraveller:5}")
  @NonNull
  private Integer noOfTravellers;

  @Value("${car.rental.ac:true}")
  @NonNull
  private Boolean airConditioner;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    try {
      log.info("data initialization");
      dataUtil.initialize();
      carRentalBookingService.calculateTripFare(
          vehicleType, fuelType, destination, noOfTravellers, airConditioner);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
