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

public class VehicleTypeTest {

  private static Set<ConstraintViolation<VehicleType>> violations;
  private static Validator validator;
  private static VehicleType vehicleType;

  @BeforeAll
  public static void setup() {
    vehicleType = new VehicleType();
    vehicleType.setVehicleId(1);
    vehicleType.setType("CAR");
    vehicleType.setMaxPassengerCapacity(5);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testGetType() {
    String type = vehicleType.getType();
    Assertions.assertEquals("CAR", type);
  }

  @Test
  public void testGetMaxPassengerCapacity() {
    Integer maxPassengerCapacity = vehicleType.getMaxPassengerCapacity();
    Assertions.assertEquals(5, maxPassengerCapacity);
  }

  @Test
  public void validateVehicleTypeRequest() {
    VehicleType vehicleType = new VehicleType();
    vehicleType.setVehicleId(1);
    vehicleType.setType("PETROL");
    vehicleType.setMaxPassengerCapacity(5);
    violations = validator.validate(vehicleType);
    assertTrue(violations.isEmpty());
    assertTrue((violations.size() == 0));

    // when type is empty
    vehicleType.setType("");
    violations = validator.validate(vehicleType);
    assertTrue((violations.size() == 1));

    // when maxPassengerCapacity is null
    vehicleType.setMaxPassengerCapacity(null);
    violations = validator.validate(vehicleType);
    assertTrue((violations.size() == 2));
  }
}
