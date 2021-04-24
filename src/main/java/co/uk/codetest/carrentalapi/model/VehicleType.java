package co.uk.codetest.carrentalapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class VehicleType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long vehicleId;

  @NotBlank private String type;
  @NotNull private Integer maxPassengerCapacity;

  public VehicleType(String type, Integer maxPassengerCapacity) {
    this.type = type;
    this.maxPassengerCapacity = maxPassengerCapacity;
  }
}
