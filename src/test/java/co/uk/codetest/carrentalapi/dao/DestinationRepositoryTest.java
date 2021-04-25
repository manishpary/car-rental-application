package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.Destination;
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
public class DestinationRepositoryTest {
  @Autowired private DestinationRepository destinationRepository;
  private String city;

  @BeforeAll
  public void setUp() {
    city = "Mumbai";
    destinationRepository.save(new Destination(city, 200d));
  }

  @Test
  public void testFindByCityIgnoreCase() {
    Destination destination = destinationRepository.findByCityIgnoreCase(city);
    Assertions.assertEquals(200d, destination.getDistance());
  }
}
