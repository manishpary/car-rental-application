package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.Destination;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class DestinationRepositoryTest {
  @Autowired private DestinationRepository destinationRepository;

  private static final String CITY = "Mumbai";

  @Test
  public void testFindByCityIgnoreCase() {
    destinationRepository.save(new Destination("Mumbai", 200d));
    Destination destination = destinationRepository.findByCityIgnoreCase(CITY);
    assertThat(destination.getDistance());
  }
}
