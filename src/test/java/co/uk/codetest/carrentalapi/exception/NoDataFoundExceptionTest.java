package co.uk.codetest.carrentalapi.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NoDataFoundExceptionTest {

  @Test
  public void testNoDataFoundException() {
    NoDataFoundException noDataFoundException = new NoDataFoundException("data is not available");
    assertNotNull(noDataFoundException);
    Assertions.assertEquals("data is not available", noDataFoundException.getMessage());
  }
}
