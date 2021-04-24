package co.uk.codetest.carrentalapi.util;

import java.math.BigDecimal;

public class Constants {
  public static final class RATELIST {

    public static final BigDecimal STANDARD_RATE_PETROL = new BigDecimal(15);
    public static final BigDecimal AC_ADDITIONAL_CHARGE = new BigDecimal(2);
    public static final BigDecimal STANDARD_RATE_DIESEL =
        STANDARD_RATE_PETROL.subtract(new BigDecimal(1));
    public static final BigDecimal DISCOUNT_ON_BUS = new BigDecimal(2);
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final BigDecimal ADDITIONAL_CHARGE_OVER_CAPACITY = new BigDecimal(1);
  }
}
