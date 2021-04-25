package co.uk.codetest.carrentalapi.util;

import co.uk.codetest.carrentalapi.dao.DestinationRepository;
import co.uk.codetest.carrentalapi.dao.FuelTypeRepository;
import co.uk.codetest.carrentalapi.dao.VehicleTypeRepository;
import co.uk.codetest.carrentalapi.model.Destination;
import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Profile("default")
public class DataUtil implements InitializingBean {
  private final VehicleTypeRepository vehicleTypeRepository;

  private final FuelTypeRepository fuelTypeRepository;

  private final DestinationRepository destinationRepository;

  private final Environment environment;

  public DataUtil(
      VehicleTypeRepository vehicleTypeRepository,
      FuelTypeRepository fuelTypeRepository,
      DestinationRepository destinationRepository,
      Environment environment) {
    this.vehicleTypeRepository = vehicleTypeRepository;
    this.fuelTypeRepository = fuelTypeRepository;
    this.destinationRepository = destinationRepository;
    this.environment = environment;
  }

  public void initialize() {
    destinationRepository.save(new Destination("Pune", 0d));
    destinationRepository.save(new Destination("Mumbai", 200d));
    destinationRepository.save(new Destination("Bangalore", 1000d));
    destinationRepository.save(new Destination("Delhi", 2050d));
    destinationRepository.save(new Destination("Chennai", 1234.5));
    vehicleTypeRepository.save(new VehicleType("CAR", 5));
    vehicleTypeRepository.save(new VehicleType("SUV", 8));
    vehicleTypeRepository.save(new VehicleType("VAN", 6));
    vehicleTypeRepository.save(new VehicleType("BUS", 40));
    fuelTypeRepository.save(new FuelType("PETROL"));
    fuelTypeRepository.save(new FuelType("DIESEL"));
  }

  @Override
  public void afterPropertiesSet() {
    List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
    if (activeProfiles.contains("default")) {
      log.info("data initialization");
      initialize();
    }
  }
}
