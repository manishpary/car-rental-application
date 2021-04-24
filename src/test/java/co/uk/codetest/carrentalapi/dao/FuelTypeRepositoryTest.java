package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.FuelType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class FuelTypeRepositoryTest {
  @Autowired private FuelTypeRepository fuelTypeRepository;
  private static final String TYPE = "PETROL";

  @Test
  public void testFindByTypeIgnoreCasee() {
    fuelTypeRepository.save(new FuelType("PETROL"));
    FuelType fuelType = fuelTypeRepository.findByTypeIgnoreCase(TYPE);
    assertThat(fuelType.getType());
  }
}
