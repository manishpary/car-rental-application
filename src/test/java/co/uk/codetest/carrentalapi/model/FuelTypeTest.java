package co.uk.codetest.carrentalapi.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FuelTypeTest {
  private static Set<ConstraintViolation<FuelType>> violations;
  private static Validator validator;
  private static FuelType fuelType;

  @BeforeAll
  public static void setup() {
    fuelType = new FuelType();
    fuelType.setFuelTypeId(1);
    fuelType.setType("PETROL");

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testGetType() {
    String type = fuelType.getType();
    assertThat(type).isEqualTo("PETROL");
  }

  @Test
  public void validateFuelTypeRequest() {
    FuelType fuelType = new FuelType();
    fuelType.setFuelTypeId(1);
    fuelType.setType("PETROL");
    violations = validator.validate(fuelType);
    assertTrue(violations.isEmpty());
    assertTrue((violations.size() == 0));

    // when type is empty
    fuelType.setType("");
    violations = validator.validate(fuelType);
    assertTrue((violations.size() == 1));
  }
}
