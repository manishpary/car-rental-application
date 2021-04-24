package co.uk.codetest.carrentalapi.service;

import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;

import java.math.BigDecimal;

public interface ExpenseCalculator {
  /**
   * @param vehicleType
   * @param fuelType
   * @param destination
   * @param numberOfPeopleTravelling
   * @param isAirconditioningRequired
   * @return
   */
  BigDecimal calculateExpense(
      VehicleType vehicleType,
      FuelType fuelType,
      String destination,
      Integer numberOfPeopleTravelling,
      Boolean isAirconditioningRequired);
}
