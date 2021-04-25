package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.FuelType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@TestInstance(PER_CLASS)
public class FuelTypeRepositoryTest {
  @Autowired private FuelTypeRepository fuelTypeRepository;
  private String type;

  @BeforeAll
  public void setUp() {
    type = "Petrol";
    fuelTypeRepository.save(new FuelType(type));
  }

  @Test
  public void testFindByTypeIgnoreCasee() {
    FuelType fuelType = fuelTypeRepository.findByTypeIgnoreCase(type);
    Assertions.assertEquals(type, fuelType.getType());
  }
}
