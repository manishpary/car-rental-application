package co.uk.codetest.carrentalapi.service;

import co.uk.codetest.carrentalapi.dao.DestinationRepository;
import co.uk.codetest.carrentalapi.model.Destination;
import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static co.uk.codetest.carrentalapi.util.Constants.RATELIST.STANDARD_RATE_DIESEL;
import static co.uk.codetest.carrentalapi.util.Constants.RATELIST.STANDARD_RATE_PETROL;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@TestInstance(PER_CLASS)
@RunWith(PowerMockRunner.class)
public class ExpenseCalculatorServiceTest {

  ExpenseCalculatorService expenseCalculatorService;
  private DestinationRepository destinationRepository;
  private VehicleType vehicleType;

  private FuelType fuelType;

  @BeforeAll
  public void setUp() {
    destinationRepository = Mockito.mock(DestinationRepository.class);
    expenseCalculatorService = spy(new ExpenseCalculatorService(destinationRepository));
    vehicleType = new VehicleType("CAR", 5);
    fuelType = new FuelType("PETROL");
  }

  @Test
  public void testStandardRateBasedOnFuelType() {
    BigDecimal standardPetrolRate =
        ReflectionTestUtils.invokeMethod(
            expenseCalculatorService, "standardRateBasedOnFuelType", "PETROL");
    BigDecimal standardDieselRate =
        ReflectionTestUtils.invokeMethod(
            expenseCalculatorService, "standardRateBasedOnFuelType", "DIESEL");
    BigDecimal standardDefaultRate =
        ReflectionTestUtils.invokeMethod(
            expenseCalculatorService, "standardRateBasedOnFuelType", "DEFAULT");
    Assertions.assertEquals(STANDARD_RATE_PETROL, standardPetrolRate);
    Assertions.assertEquals(STANDARD_RATE_DIESEL, standardDieselRate);
    Assertions.assertEquals(BigDecimal.ZERO, standardDefaultRate);
  }

  @Test
  public void testDiscountBasedOnVehicleType() {
    BigDecimal discountRate =
        ReflectionTestUtils.invokeMethod(
            expenseCalculatorService, "discountBasedOnVehicleType", "BUS", STANDARD_RATE_PETROL);
    Assertions.assertEquals(
        new BigDecimal(14.70).setScale(2, BigDecimal.ROUND_HALF_UP), discountRate);
  }

  @Test
  public void testStandardRateBasedOnVehicleCapacity() {
    Integer maxCapacity = 5;
    Integer noOfPassenger = 10;
    BigDecimal standardRateOnPassengerCapacity =
        ReflectionTestUtils.invokeMethod(
            expenseCalculatorService,
            "standardRateBasedOnVehicleCapacity",
            noOfPassenger,
            maxCapacity,
            STANDARD_RATE_PETROL);
    Assertions.assertEquals(new BigDecimal(20), standardRateOnPassengerCapacity);
  }

  @Test
  public void testCalculateTotalExpense() throws Exception {
    Destination destination = new Destination("MUMBAI", 200.0d);
    when(destinationRepository.findByCityIgnoreCase("MUMBAI")).thenReturn(destination);
    expenseCalculatorService.calculateTotalExpense(vehicleType, fuelType, "MUMBAI", 5, true);
    /*  PowerMockito.verifyPrivate(expenseCalculatorService, times(1))
          .invoke("calculateExpense", vehicleType, fuelType, "MUMBAI", 5, true);
    */ }
}
