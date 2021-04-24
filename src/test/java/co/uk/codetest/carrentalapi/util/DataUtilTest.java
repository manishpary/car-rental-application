package co.uk.codetest.carrentalapi.util;

import co.uk.codetest.carrentalapi.dao.DestinationRepository;
import co.uk.codetest.carrentalapi.dao.FuelTypeRepository;
import co.uk.codetest.carrentalapi.dao.VehicleTypeRepository;
import co.uk.codetest.carrentalapi.model.Destination;
import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataUtilTest {

  @Autowired private VehicleTypeRepository vehicleTypeRepository;

  @Autowired private FuelTypeRepository fuelTypeRepository;

  @Autowired private DestinationRepository destinationRepository;

  @Test
  public void testInitialize() {
    vehicleTypeRepository.save(new VehicleType("CAR", 5));
    fuelTypeRepository.save(new FuelType("PETROL"));
    destinationRepository.save(new Destination("Pune", 0.0));

    Assert.assertTrue(
        vehicleTypeRepository.findByTypeIgnoreCase("CAR").getMaxPassengerCapacity() == 5);
    Assert.assertTrue(fuelTypeRepository.findByTypeIgnoreCase("PETROL").getType().equals("PETROL"));
    Assert.assertTrue(destinationRepository.findByCityIgnoreCase("Pune").getDistance() == 0.0);
  }
}
