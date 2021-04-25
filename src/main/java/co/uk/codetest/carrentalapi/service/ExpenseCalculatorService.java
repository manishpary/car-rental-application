package co.uk.codetest.carrentalapi.service;

import co.uk.codetest.carrentalapi.dao.DestinationRepository;
import co.uk.codetest.carrentalapi.exception.NoDataFoundException;
import co.uk.codetest.carrentalapi.model.Destination;
import co.uk.codetest.carrentalapi.model.FuelType;
import co.uk.codetest.carrentalapi.model.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static co.uk.codetest.carrentalapi.util.Constants.RATELIST.*;

@Service
@Slf4j
public class ExpenseCalculatorService {

  private final DestinationRepository destinationRepository;

  private final ExpenseCalculator expenseCalculatorFunction = this::calculateExpense;

  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> additionFunc = BigDecimal::add;

  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> subtractFunc = BigDecimal::subtract;

  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> multiplyFunc = BigDecimal::multiply;

  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> divideFunc = BigDecimal::divide;

  private final BiPredicate<Integer, Integer> isVehicleCapacityFull =
      (value1, value2) -> (value1 > value2);

  public ExpenseCalculatorService(DestinationRepository destinationRepository) {
    this.destinationRepository = destinationRepository;
  }

  /**
   * @param vehicleType
   * @param fuelType
   * @param destination
   * @param numberOfPeopleTravelling
   * @param isAirconditioningRequired
   * @return
   */
  public BigDecimal calculateTotalExpense(
      VehicleType vehicleType,
      FuelType fuelType,
      String destination,
      Integer numberOfPeopleTravelling,
      Boolean isAirconditioningRequired) {
    return expenseCalculatorFunction.calculateExpense(
        vehicleType, fuelType, destination, numberOfPeopleTravelling, isAirconditioningRequired);
  }

  /**
   * @param vehicleType
   * @param fuelType
   * @param destination
   * @param numberOfPeopleTravelling
   * @param isAirconditioningRequired
   * @return
   */
  private BigDecimal calculateExpense(
      VehicleType vehicleType,
      FuelType fuelType,
      String destination,
      Integer numberOfPeopleTravelling,
      Boolean isAirconditioningRequired) {
    BigDecimal totalExpense = BigDecimal.ZERO;
    try {
      Integer maxCapacity = vehicleType.getMaxPassengerCapacity();
      String fuelTypeStr = fuelType.getType();
      Destination dest = destinationRepository.findByCityIgnoreCase(destination);
      if (dest == null) {
        log.error("There is no data in the database for" + " " + destination);
        throw new NoDataFoundException("data is not available");
      }
      BigDecimal distance = BigDecimal.valueOf(dest.getDistance());
      CompletableFuture<BigDecimal> expense = CompletableFuture.completedFuture(BigDecimal.ZERO);
      if (distance.compareTo(BigDecimal.ZERO) > 0) {
        expense =
            CompletableFuture.completedFuture(standardRateBasedOnFuelType(fuelTypeStr))
                .thenApply(
                    standarRate -> standardRateBasedOnDiscount(vehicleType.getType(), standarRate))
                .thenApply(
                    standarRate ->
                        standardRateBasedOnAirConditon(isAirconditioningRequired, standarRate))
                .thenApply(
                    standarRate ->
                        standardRateBasedOnVehicleCapacity(
                            numberOfPeopleTravelling, maxCapacity, standarRate));
      }
      totalExpense = multiplyFunc.apply(distance, expense.get()).setScale(2, RoundingMode.HALF_UP);
      printOutPut(
          vehicleType,
          fuelType,
          destination,
          numberOfPeopleTravelling,
          isAirconditioningRequired,
          totalExpense);

    } catch (ExecutionException | InterruptedException e) {
      log.error(e.getMessage());
    }
    return totalExpense;
  }

  /**
   * @param fuelTypeStr
   * @return
   */
  private BigDecimal standardRateBasedOnFuelType(String fuelTypeStr) {
    switch (fuelTypeStr.toUpperCase()) {
      case "PETROL":
        return STANDARD_RATE_PETROL;
      case "DIESEL":
        return STANDARD_RATE_DIESEL;
      default:
        return BigDecimal.ZERO;
    }
  }

  /**
   * @param vehicleType
   * @param standardRate
   * @return
   */
  private BigDecimal standardRateBasedOnDiscount(String vehicleType, BigDecimal standardRate) {
    if (vehicleType.equalsIgnoreCase("BUS")) {
      standardRate =
          subtractFunc.apply(
              standardRate,
              multiplyFunc.apply(DISCOUNT_ON_BUS, (divideFunc.apply(standardRate, ONE_HUNDRED))));
    }
    return standardRate;
  }

  /**
   * @param numberOfPeopleTravelling
   * @param maxCapacity
   * @param standardRate
   * @return
   */
  private BigDecimal standardRateBasedOnVehicleCapacity(
      Integer numberOfPeopleTravelling, Integer maxCapacity, BigDecimal standardRate) {
    if (isVehicleCapacityFull.test(numberOfPeopleTravelling, maxCapacity)) {
      Integer additionalPeople = numberOfPeopleTravelling - maxCapacity;
      standardRate =
          additionFunc.apply(
              standardRate,
              multiplyFunc.apply(
                  new BigDecimal(additionalPeople), ADDITIONAL_CHARGE_OVER_CAPACITY));
    }
    return standardRate;
  }

  private BigDecimal standardRateBasedOnAirConditon(
      Boolean isAirConditionRequired, BigDecimal standarRate) {
    return isAirConditionRequired
        ? additionFunc.apply(standarRate, AC_ADDITIONAL_CHARGE)
        : standarRate;
  }

  private void printOutPut(
      VehicleType vehicleType,
      FuelType fuelType,
      String destination,
      Integer numberOfPeopleTravelling,
      Boolean isAirconditioningRequired,
      BigDecimal totalExpense) {
    System.out.println(
        "Vehicle Type  : "
            + vehicleType.getType()
            + System.lineSeparator()
            + "Fuel Type : "
            + fuelType.getType()
            + System.lineSeparator()
            + "Destination : "
            + destination
            + System.lineSeparator()
            + "Number of People Travelling : "
            + numberOfPeopleTravelling
            + System.lineSeparator()
            + "Max Passenger Capacity of Selected Vehicle:"
            + vehicleType.getMaxPassengerCapacity()
            + System.lineSeparator()
            + "Is Air Conditioning Required: "
            + isAirconditioningRequired
            + System.lineSeparator()
            + System.lineSeparator()
            + "Total Expenses = "
            + totalExpense
            + "/-");
  }
}
