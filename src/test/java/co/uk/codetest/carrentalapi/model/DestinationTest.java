package co.uk.codetest.carrentalapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DestinationTest {
  private static Set<ConstraintViolation<Destination>> violations;
  private static Validator validator;
  private static Destination destination;

  @BeforeAll
  public static void setup() {
    destination = new Destination();
    destination.setDestinationId(1);
    destination.setCity("MUMBAI");
    destination.setDistance(200.0);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testGetCity() {
    String city = destination.getCity();
    Assertions.assertEquals("MUMBAI", city);
  }

  @Test
  public void testGetDistance() {
    Double distance = destination.getDistance();
    Assertions.assertEquals(200.0, distance);
  }

  @Test
  public void validateDestinationRequest() {
    Destination destination = new Destination();
    destination.setDestinationId(1);
    destination.setDistance(200.0);
    destination.setCity("MUMBAI");
    violations = validator.validate(destination);
    assertTrue(violations.isEmpty());
    assertTrue((violations.size() == 0));

    // when city is empty
    destination.setCity("");
    violations = validator.validate(destination);
    assertTrue((violations.size() == 1));

    // when distance is null
    destination.setDistance(null);
    violations = validator.validate(destination);
    assertTrue((violations.size() == 2));
  }
}
