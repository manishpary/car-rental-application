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
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static co.uk.codetest.carrentalapi.util.Constants.RATELIST.*;

@Service
@Slf4j
public class ExpenseCalculatorService {

  private final DestinationRepository destinationRepository;
  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> additionFunc = BigDecimal::add;
  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> subtractFunc = BigDecimal::subtract;
  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> multiplyFunc = BigDecimal::multiply;
  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> divideFunc = BigDecimal::divide;
  private final BiPredicate<Integer, Integer> isVehicleCapacityFull =
      (value1, value2) -> (value1 > value2);
  private final ExpenseCalculator expenseCalculatorFunction = this::calculateExpense;

  public ExpenseCalculatorService(DestinationRepository destinationRepository) {
    this.destinationRepository = destinationRepository;
  }

  /**
   * @param vehicleType vehicle type ex car,van,bus,suv
   * @param fuelType fuel type ex petrol,diesel
   * @param destination destination ex mumbai,chennai
   * @param numberOfPeopleTravelling ex no. of traveller
   * @param isAirconditioningRequired ex air condition required or not
   * @return Total expense
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
   * @param vehicleType vehicle type ex car,van,bus,suv
   * @param fuelType fuel type ex petrol,diesel
   * @param destination destination ex mumbai,chennai
   * @param numberOfPeopleTravelling ex no. of traveller
   * @param isAirconditioningRequired ex air condition required or not
   * @return total expense
   */
  private BigDecimal calculateExpense(
      VehicleType vehicleType,
      FuelType fuelType,
      String destination,
      Integer numberOfPeopleTravelling,
      Boolean isAirconditioningRequired) {
    BigDecimal totalExpense;
    BigDecimal expense = BigDecimal.ZERO;
    Destination dest = destinationRepository.findByCityIgnoreCase(destination);
    if (dest == null) {
      log.error("There is no data in the database for" + " " + destination);
      throw new NoDataFoundException("data is not available");
    }
    BigDecimal distance = BigDecimal.valueOf(dest.getDistance());
    if (distance.compareTo(BigDecimal.ZERO) > 0) {
      Function<String, BigDecimal> standardRateBasedOnFuelTypeFunc =
          fuelTypeStr -> standardRateBasedOnFuelType(fuelType.getType());
      Function<BigDecimal, BigDecimal> standardRateBasedOnDiscountFunc =
          standardRate -> standardRateBasedOnDiscount(vehicleType.getType(), standardRate);
      Function<BigDecimal, BigDecimal> standardRateBasedOnAirConditionFunc =
          standardRate -> standardRateBasedOnAirCondition(isAirconditioningRequired, standardRate);
      Function<BigDecimal, BigDecimal> standardRateBasedOnVehicleCapacityFunc =
          standardRate ->
              standardRateBasedOnVehicleCapacity(
                  numberOfPeopleTravelling, vehicleType.getMaxPassengerCapacity(), standardRate);
      expense =
          standardRateBasedOnFuelTypeFunc
              .andThen(
                  standardRateBasedOnDiscountFunc.andThen(
                      standardRateBasedOnAirConditionFunc.andThen(
                          standardRateBasedOnVehicleCapacityFunc)))
              .apply(fuelType.getType());
    }

    totalExpense = multiplyFunc.apply(distance, expense).setScale(2, RoundingMode.HALF_UP);
    printOutPut(
        vehicleType,
        fuelType,
        destination,
        numberOfPeopleTravelling,
        isAirconditioningRequired,
        totalExpense);

    return totalExpense;
  }

  /**
   * @param fuelTypeStr fuel type ex PETROL, DIESEL
   * @return rate based on fuel type
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
   * @param vehicleType ex CAR,BUS
   * @param standardRate standard Rate
   * @return Rate based on Discount
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
   * @param numberOfPeopleTravelling no of traveller
   * @param maxCapacity max vehicle capacity
   * @param standardRate standard rate
   * @return standardRate based on vehicle capacity
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

  private BigDecimal standardRateBasedOnAirCondition(
      Boolean isAirConditionRequired, BigDecimal standardRate) {
    return isAirConditionRequired
        ? additionFunc.apply(standardRate, AC_ADDITIONAL_CHARGE)
        : standardRate;
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
