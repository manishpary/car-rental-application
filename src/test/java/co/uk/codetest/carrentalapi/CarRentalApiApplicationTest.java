package co.uk.codetest.carrentalapi;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarRentalApiApplicationTest {
  private CarRentalApiApplication launcher = new CarRentalApiApplication();

  @Test
  public void contextLoads() {
    assertThat(launcher).isNotNull();
  }

  @Test
  public void test() {
    CarRentalApiApplication.main(new String[] {"--spring.main.web-environment=true"});
  }
}
