package co.uk.codetest.carrentalapi.util;

import co.uk.codetest.carrentalapi.dao.DestinationRepository;
import co.uk.codetest.carrentalapi.dao.FuelTypeRepository;
import co.uk.codetest.carrentalapi.dao.VehicleTypeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestInstance(PER_CLASS)
public class DataUtilTest {

  @Autowired private VehicleTypeRepository vehicleTypeRepository;

  @Autowired private FuelTypeRepository fuelTypeRepository;

  @Autowired private DestinationRepository destinationRepository;

  @Mock private Environment environment;

  DataUtil dataUtil;

  @BeforeAll
  public void setUp() {
    environment = Mockito.mock(Environment.class);
    dataUtil =
        new DataUtil(vehicleTypeRepository, fuelTypeRepository, destinationRepository, environment);
  }

  @Test
  public void testInitialize() {
    dataUtil.initialize();
    Assert.assertTrue(
        vehicleTypeRepository.findByTypeIgnoreCase("CAR").getMaxPassengerCapacity() == 5);
    Assert.assertTrue(fuelTypeRepository.findByTypeIgnoreCase("PETROL").getType().equals("PETROL"));
    Assert.assertTrue(destinationRepository.findByCityIgnoreCase("Pune").getDistance() == 0.0);
  }

  @Test
  public void testAfterPropertiesSet() {
    String[] activeProfiles = new String[] {"default", "local"};
    when(environment.getActiveProfiles()).thenReturn(activeProfiles);
    dataUtil.afterPropertiesSet();
    Assert.assertTrue(
        vehicleTypeRepository.findByTypeIgnoreCase("CAR").getMaxPassengerCapacity() == 5);
    Assert.assertTrue(fuelTypeRepository.findByTypeIgnoreCase("PETROL").getType().equals("PETROL"));
    Assert.assertTrue(destinationRepository.findByCityIgnoreCase("Pune").getDistance() == 0.0);
  }
}
