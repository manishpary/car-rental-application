package co.uk.codetest.carrentalapi;

import co.uk.codetest.carrentalapi.service.CarRentalBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CarRentalApiStartupRunner implements ApplicationRunner {
  private final CarRentalBookingService carRentalBookingService;

  @Value("${car.rental.vehicltype:CAR}")
  private String vehicleType;

  @Value("${car.rental.fueltype:PETROL}")
  private String fuelType;

  @Value("${car.rental.destination:MUMBAI}")
  private String destination;

  @Value("${car.rental.nooftraveller:5}")
  private Integer noOfTravellers;

  @Value("${car.rental.ac:true}")
  private Boolean airConditioner;

  public CarRentalApiStartupRunner(CarRentalBookingService carRentalBookingService) {
    this.carRentalBookingService = carRentalBookingService;
  }

  @Override
  public void run(ApplicationArguments args) {
    try {
      carRentalBookingService.calculateTripFare(
          vehicleType, fuelType, destination, noOfTravellers, airConditioner);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
