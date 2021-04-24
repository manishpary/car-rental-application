package co.uk.codetest.carrentalapi.dao;

import co.uk.codetest.carrentalapi.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
  VehicleType findByTypeIgnoreCase(String type);
}
