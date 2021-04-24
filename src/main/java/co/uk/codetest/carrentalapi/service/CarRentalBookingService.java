package co.uk.codetest.carrentalapi.service;

import co.uk.codetest.carrentalapi.dao.FuelTypeRepository;
import co.uk.codetest.carrentalapi.dao.VehicleTypeRepository;
import co.uk.codetest.carrentalapi.exception.NoDataFoundException;
import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class CarRentalBookingService {

  private final VehicleTypeRepository vehicleTypeRepository;

  private final FuelTypeRepository fuelTypeRepository;

  private final ExpenseCalculatorService expenseCalculatorService;

  public CarRentalBookingService(
      VehicleTypeRepository vehicleTypeRepository,
      FuelTypeRepository fuelTypeRepository,
      ExpenseCalculatorService expenseCalculatorService) {
    this.vehicleTypeRepository = vehicleTypeRepository;
    this.fuelTypeRepository = fuelTypeRepository;
    this.expenseCalculatorService = expenseCalculatorService;
  }

  /**
   * @param vehicleType
   * @param fuelType
   * @param destination
   * @param numberOfPeopleTravelling
   * @param isAirConditioningRequired
   * @return
   */
  public BigDecimal calculateTripFare(
      String vehicleType,
      String fuelType,
      String destination,
      Integer numberOfPeopleTravelling,
      Boolean isAirConditioningRequired) {
    VehicleType vehicleTypeObj = vehicleTypeRepository.findByTypeIgnoreCase(vehicleType);
    FuelType fuelTypeObj = fuelTypeRepository.findByTypeIgnoreCase(fuelType);
    if (vehicleTypeObj == null || fuelTypeObj == null) {
      log.error("There is no data in the database for" + " " + vehicleType);
      throw new NoDataFoundException("data is not available");
    }
    return expenseCalculatorService.calculateTotalExpense(
        vehicleTypeObj,
        fuelTypeObj,
        destination,
        numberOfPeopleTravelling,
        isAirConditioningRequired);
  }
}
