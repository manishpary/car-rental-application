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
public class Destination {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long destinationId;

  @NotBlank private String city;

  @NotNull private Double distance;

  public Destination(String city, Double distance) {
    this.city = city;
    this.distance = distance;
  }
}
