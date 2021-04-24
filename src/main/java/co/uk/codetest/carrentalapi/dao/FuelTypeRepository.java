package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
  FuelType findByTypeIgnoreCase(String type);
}
