package co.uk.codetest.carrentalapi.util;

import co.uk.codetest.carrentalapi.dao.DestinationRepository;
import co.uk.codetest.carrentalapi.dao.FuelTypeRepository;
import co.uk.codetest.carrentalapi.dao.VehicleTypeRepository;
import co.uk.codetest.carrentalapi.model.Destination;
import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataUtil {
  @Autowired private VehicleTypeRepository vehicleTypeRepository;

  @Autowired private FuelTypeRepository fuelTypeRepository;

  @Autowired private DestinationRepository destinationRepository;

  public void initialize() {
    vehicleTypeRepository.save(new VehicleType("CAR", 5));
    vehicleTypeRepository.save(new VehicleType("SUV", 8));
    vehicleTypeRepository.save(new VehicleType("VAN", 6));
    vehicleTypeRepository.save(new VehicleType("BUS", 40));
    fuelTypeRepository.save(new FuelType("PETROL"));
    fuelTypeRepository.save(new FuelType("DIESEL"));
    destinationRepository.save(new Destination("Pune", 0d));
    destinationRepository.save(new Destination("Mumbai", 200d));
    destinationRepository.save(new Destination("Bangalore", 1000d));
    destinationRepository.save(new Destination("Delhi", 2050d));
    destinationRepository.save(new Destination("Chennai", 1234.5));
  }
}
