package co.uk.codetest.carrentalapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
public class FuelType {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long fuelTypeId;

  @NotBlank private String type;

  public FuelType(String type) {
    this.type = type;
  }
}
