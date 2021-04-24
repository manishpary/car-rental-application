package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class VehicleTypeRepositoryTest {
  @Autowired private VehicleTypeRepository vehicleTypeRepository;
  private static final String TYPE = "CAR";

  @Test
  public void testFindByTypeIgnoreCase() {
    vehicleTypeRepository.save(new VehicleType("CAR", 5));
    VehicleType vehicleType = vehicleTypeRepository.findByTypeIgnoreCase(TYPE);
    assertThat(vehicleType.getType());
  }
}
