package co.uk.codetest.carrentalapi.service;

import co.uk.codetest.carrentalapi.dao.FuelTypeRepository;
import co.uk.codetest.carrentalapi.dao.VehicleTypeRepository;
import co.uk.codetest.carrentalapi.exception.NoDataFoundException;
import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(PER_CLASS)
public class CarRentalBookingServiceTest {

  private ExpenseCalculatorService expenseCalculatorService;

  private VehicleTypeRepository vehicleTypeRepository;

  private FuelTypeRepository fuelTypeRepository;

  private CarRentalBookingService carRentalBookingService;

  private VehicleType vehicleType;

  private FuelType fuelType;

  @BeforeAll
  public void setUp() {
    expenseCalculatorService = Mockito.mock(ExpenseCalculatorService.class);
    vehicleTypeRepository = Mockito.mock(VehicleTypeRepository.class);
    fuelTypeRepository = Mockito.mock(FuelTypeRepository.class);
    carRentalBookingService =
        new CarRentalBookingService(
            vehicleTypeRepository, fuelTypeRepository, expenseCalculatorService);
    vehicleType = new VehicleType("CAR", 5);
    fuelType = new FuelType("PETROL");
  }

  @Test
  public void testCalculateTripFare() {
    when(vehicleTypeRepository.findByTypeIgnoreCase("CAR")).thenReturn(vehicleType);
    when(fuelTypeRepository.findByTypeIgnoreCase("PETROL")).thenReturn(fuelType);
    when(expenseCalculatorService.calculateTotalExpense(any(), any(), any(), any(), any()))
        .thenReturn(new BigDecimal(3400));
    BigDecimal totalExpense =
        carRentalBookingService.calculateTripFare("CAR", "PETROL", "MUMBAI", 5, true);
    Assertions.assertNotNull(totalExpense);
  }

  @Test
  public void testCalculateTripFareWhenNoDataFoundException() {
    when(vehicleTypeRepository.findByTypeIgnoreCase("CAR")).thenReturn(null);
    Assertions.assertThrows(
        NoDataFoundException.class,
        () -> {
          carRentalBookingService.calculateTripFare("CAR", "PETROL", "MUMBAI", 5, true);
        });
  }
}
